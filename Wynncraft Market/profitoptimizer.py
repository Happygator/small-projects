import sys
def addTax(initial):
    return round(initial/1.05 + 0.0000001)
def removeTax(final):
    return round(final/1.05)
print("Enter quantity then price, done to submit")
print("Ascending order of price or this tool breaks")
marketprices = []
max_spending = 65536 # 16 LE
typed = ""
while typed != "done":
    typed = input("> ")
    nums = typed.split()
    if typed == "done":
        continue
    if len(nums) != 2:
        print("invalid input")
        continue
    if not nums[0].isnumeric() or not nums[1].isnumeric():
        print("invalid input")
        continue
    marketprices.append([int(nums[0]), int(nums[1])])
maxprofit = 0
profitindex = 0
totalspent = marketprices[0][0] * marketprices[0][1]
numbought = [0, marketprices[0][0]]
costs = [0, totalspent]
for i in range(1, len(marketprices)):
    
    #buy everything under, then sell at this price
    newprice = addTax(removeTax(marketprices[i][1]) - 1)
    """
    print("---------")
    print("new price = " + str(newprice))
    print("items to sell = " + str(numbought[i]))
    print("total spent = " + str(totalspent))
    print("---------")
    """
    if numbought[i] * newprice - totalspent > maxprofit:
        maxprofit = numbought[i] * newprice - totalspent
        profitindex = i
    totalspent += marketprices[i][0] * marketprices[i][1]
    costs.append(totalspent)
    numbought.append(marketprices[i][0] + numbought[-1])
if profitindex == 0:
    print("no profit lmao")
    sys.exit(0)
print("max profit when buying until " + str(marketprices[profitindex][1]))
print("expected spending = " + str(costs[profitindex]))
print("expected revenue = " + str(numbought[profitindex] * addTax(removeTax(marketprices[profitindex][1])-1)))
totalprofit = numbought[profitindex] * addTax(removeTax(marketprices[profitindex][1])-1) - costs[profitindex]
print("expected profit = " + str(totalprofit) + " = " + str(totalprofit/64) + "EB")
print("sell for " + str(removeTax(marketprices[profitindex][1])-1))
"""
print(costs)
print(numbought)
print(profitindex)
print(addTax(removeTax(marketprices[profitindex][1])-1))
"""
