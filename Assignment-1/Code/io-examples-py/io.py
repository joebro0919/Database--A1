import csv

def myreader(filename:str):
    with open(filename, newline='') as f:
        reader = csv.reader(f)
        your_list = list(reader)

    print(your_list)

def mywriter(filename:str, mylist:list):
    with open(filename, 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
        # write multiple rows
        writer.writerows(mylist)

def main():
    mydata = [
        [1,'Hayes','Corey','123 Wern Ddu Lane','LUSTLEIGH',23],
        [2,'Macdonald','Charlie','23 Peachfield Road','CEFN EINION',45],
        [3,'Frost','Emma','85 Kingsway North','HOLTON',26],
        [4,'Thomas','Tom','59 Dover Road','WESTER GRUINARDS',51],
        [5,'Khan','Janice','72 Ballifeary Road','BANCFFOSFELEN',11]]

    myreader('customer.csv')
    mywriter('output.csv', mydata)

main()
