import twint
from transformers import pipeline
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
import re
import emoji
import html.parser   
import math
from ast import literal_eval
from wordcloud import WordCloud, STOPWORDS
from PIL import Image
import ast
pd.set_option('display.max_colwidth', None)

def _clean_tweet(text):
    
    '''
    Clean tweet by removing,urls, 
    emojis, multiple whitespaces and
    escape html
    
    Args:
        text(string): text to be cleaned
        
    Returns:
        output(string): cleaned text
    
    
    '''
    
    output = re.sub('http[\w\d\D./]+', '', text).strip() #remove url
    output = emoji.replace_emoji(output, replace='') # remove emoji
    output = re.sub('\s\s+', ' ', output).strip() # remove white spaces
    output = html.unescape(output) # escape html
    output = output.lower() # lower case

    return output

def clean_tweet(df):
    
    '''
    Apply _clean_tweet() on a pd.Series
    
    Args:
        df(pandas.DataFrame): DataFrame containing column with
        text to be cleaned
        
    Return:
        df(pandas.DataFrame): DataFrame with cleaned text
    
    '''
    
    df['clean_text'] = df['tweet'].apply(lambda x: _clean_tweet(x))
    
    return df

def _get_mention_handle(text):
    
    '''
    Find list of twitter handles that are mentioned
    in the tweet
    
    Args:
        text(string): tweet
        
    Returns:
        output(list): list of handles mentioned in the
        tweet
    '''
    
    output = list(set(re.findall('@\w+', text)))
    
    return output

def get_mention_handle(df):
    
    '''
    Apply _get_mention_handle on a pd.Series
    
    Args:
        df(pandas.DataFrame): contain column with tweet
        
    Returns:
        df(pandas.DataFrame): contain newly created column
        with handles mentioned in tweet
    
    '''
    
    df['handles_mentioned'] = df['tweet'].apply(lambda x: _get_mention_handle(x))
    
    return df

def _get_hashtag(text):
    
    output = list(set(re.findall('#\w+', text)))
    
    return output

def get_hashtag(df):
    
    df['hashtags'] = df['tweet'].apply(lambda x: _get_hashtag(x))
    
    return df

def clean_column_names(df):
    
    '''
    Standardize column names
    
    Args:
        df(pd.DataFrame): DataFrame with column
        names to be standardized
        
    Returns:
        df(pd.DataFrame): DataFrame with standardized
        column names    
    
    '''
    
    df.columns = [i.lower() for i in df.columns]
    
    return df

def get_date_attr(df):
    
    '''
    Find date attributes
        1. Year
        2. Month
        3. Date of Week
        4. Hour
    
    Args:
    df(pd.DataFrame): DataFrame containing date column
        
    Returns:
        df(pd.DataFrame): DataFrame date attribute columns
    
    '''
    
    df['date'] = pd.to_datetime(df['date'])
    df['year'] = df['date'].dt.year
    df['month'] = df['date'].dt.month
    df['day_of_week'] = df['date'].dt.day_name()
    df['hour'] = df['date'].dt.hour
    
    return df
"""
def format_flags(df):
    
    '''
    Change indicators from t and f to True and False 
    respectively
    
    Args:
        df(pd.DataFrame): DataFrame containing flags
        
    Returns:
        df(pd.DataFrame): DataFrame containing flags
    
    '''

    df['isflagged'] = df['isflagged'].apply(lambda x: True if x == 't' else False)
    df['isdeleted'] = df['isdeleted'].apply(lambda x: True if x == 't' else False)
    df['isretweet'] = df['isretweet'].apply(lambda x: True if x == 't' else False)

    df['isretweet'] = df['retweet_id'] != 
    
    return df
"""

def drop_tweets(df):
    '''
    Drop tweets
    
    Args:
        df(pd.DataFrame): DataFrame containing tweets
        
    Returns:
        df(pd.DataFrame): DataFrame date after removing
        blank tweets
    
    
    '''
    
    df = (df
          .loc[(df['clean_text'] != '') 
               & (df['year'].between(2012,2021)
#               & (df['isretweet'] == False)
            )]
          .reset_index(drop = True))
    
    return df

def get_retweets(df):
    
    '''
    Identify retweets
    '''
    
    df.loc[(df['tweet'].str.contains('^RT @|^\"RT @')), 'isretweet'] = True
    
    return df

new_tweets = False

if new_tweets:
    
    c = twint.Config()
    c.Filter_retweets = True
    c.Hide_output = True
    c.Limit = 500
    c.Store_csv = True
    c.Search = ['Andrea Perng']
    c.Output = "tweets.csv"
    twint.run.Search(c)
    

df = pd.read_csv('tweets.csv')

df = (df
      .pipe(clean_column_names)
      .pipe(clean_tweet)
      .pipe(get_date_attr)
      .pipe(get_hashtag)
      .pipe(get_mention_handle))
#      .pipe(format_flags)
#      .pipe(get_retweets)
#      .pipe(drop_tweets))

tweets = df['clean_text'].to_list()
df.head()
print (f'No of Tweets: {len(tweets)}')
print("\n" * 4)
#for i in tweets:
#    print(i)
    
STOPWORDS.update({'will', 's'})
stopwords = set(STOPWORDS)  

wordcloud = WordCloud(stopwords=stopwords, background_color="white")
text = ' '.join(df['clean_text'].values.tolist())
wordcloud.generate(text)
plt.imshow(wordcloud, interpolation='bilinear')
plt.axis("off")
plt.show()