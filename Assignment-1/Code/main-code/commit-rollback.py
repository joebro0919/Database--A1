# Skeleton for commit and roll-back exercise    

# *** Your Code goes Here ***

# Your main program
def main():
    print("First Output:")
    print("Print Original Contents of Databases")
    print("Print current status of Log Sub-system\n\n")

    # Transaction Block 1: Successful
    print("BLOCK TRANSACTION 1")
    print("Subtract money from one account.")
    print("Add money to second one")
    print("COMMIT all your changes")
    print("Print Contents of Databases")
    print("Print current status of Log Sub-system\n\n")

    # Transaction Block 1: Fails!
    print("BLOCK TRANSACTION 2")
    print("Subtract money from one account (Same Transaction than before)")
    print("Failure occurs!!!!!!! ACTION REQUIRED")
    print("Must either AUTOMATICALLY Roll-back Database to a state of equilibrium (Bonus), OR\nSTOP Operations and show: (a) Log-Status, and (b) Databases Contents.\n")
    print("\nThe Log Sub-system contents should show the necessary operations needed to fix the situation!")

main()



