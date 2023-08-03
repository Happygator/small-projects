import sys
TAX_RATE = 0.05 # Silverbull subscribers only have 3% tax but that would cost a comically large number emeralds or irl money, both of which i'm not spending
LE_TO_SPEND = 64 # 64 LE(262144 emeralds) because I am broke
MAX_QUANTITY = 40 # it's really hard to sell a lot of an item fast unless it's in really high demand, and often you have to undercut other people in the process and lose profit
def addTax(initial):
    return round(initial*(1 + TAX_RATE) + 0.1) # tiny bonus because 2.5 rounds to 2 in python
def removeTax(final):
    return round(final/(1 + TAX_RATE))
print("Enter quantity of item then price of item, seperated by spaces, \"done\" to submit")
print("Example: 5 629 would mean 5 items at 629 cost each")
marketprices = []
max_spending = LE_TO_SPEND * 64 * 64
typed = ""
while typed != "done":
    typed = input("> ")
    if typed == "done":
        continue
    nums = typed.split()
    if len(nums) != 2:
        print("invalid input")
        continue
    elif not nums[0].isnumeric() or not nums[1].isnumeric():
        print("invalid input")
        continue
    else:
        marketprices.append([int(nums[0]), int(nums[1])])
marketprices = sorted(marketprices, key=lambda x: x[1]) # maybe i wouldn't have to do this if the server did it well
amount_spent = 0
quantity_bought = 0
max_profit = 0
max_profit_index = 0
for i in range(len(marketprices) - 1):
    quantity_bought += marketprices[i][0]
    amount_spent += marketprices[i][0] * marketprices[i][1]
    if amount_spent > max_spending:
        break
    elif quantity_bought > MAX_QUANTITY:
        break
    profit = quantity_bought * (removeTax(marketprices[i+1][1]) - 1) - amount_spent
    if profit > max_profit:
        max_profit = profit
        max_profit_index = i
    
if max_profit == 0:
    print("no profit lol")
else:
    print(f"maximum profit is {max_profit} emeralds, or around {round(max_profit/(4096))} LE")
    print(f"buy each item {marketprices[max_profit_index][1]} and below")
    print(f"sell all for {removeTax(marketprices[max_profit_index+1][1] - 1)} emeralds")
    
    





