TRADING APPLICATION        
A trading application that allows users to trade [small, tangible] items. 


The system has two types of users, TradingUsers which are users that are able to trade items, and AdminUsers which are administrative users that control TradingUser accounts. The TradingUser will be allowed to browse for available items to trade, request a Transaction with the TradingUser owner of the desired item, and set a Meeting for the Transaction to take place. TradingUsers may request for temporary or permanent transactions, either of which may be a one-way transaction (one item is traded) or a two-way (two items are traded). TradingUsers are restricted by a number of limitations (see “How a TradingUser gets frozen”). The violation of these limitations results in the TradingUser being flagged. The TradingUser may subsequently be frozen by an AdminUser. TradingUsers may request to add an Item for trade. AdminUsers must approve or reject said Item to allow other TradingUsers to view the Item. 


How to run the system
1. Run the main method located within TradingApplication.
2. If running the program for the first time, all .ser files are automatically created if they do not already exist. Otherwise all data is saved and .ser files are updated upon log out.


How to [generally] use the system (as a AdminUser)
* View items requested for approval by TradingUsers
* View flagged TradingUser by the system, freeze or pardon these accounts
* View requests by frozen TradingUser accounts to have their account unfrozen and approve or disapprove
* Change thresholds within the system
* Create new administrators if you are using the master admin account


How to [generally] use the system (as a TradingUser)
* If you are running the application for the first time, select the create a new account option in the main menu.
* Otherwise log into your existing account.
* Add items to your inventory by submitting item requests to admins.
* Browse available items for trade to add to your wishlist or create a trade for any items of interest.
* View your active transactions to edit, confirm, or cancel them.
* If your account ever gets frozen, submit a request to the admins to unfreeze it.


How a TradingUser gets frozen:
If any of the below thresholds are reached, the system flags the TradingUser to be frozen, and the AdminUser will need to check flagged accounts to decide whether the TradingUser should actually be frozen or for their status to remain active.
*Note: these thresholds can be changed by the AdminUser
* borrowThreshold = 1
   * the minimum number of items the TradingUser needs to lend before they can borrow 
* weeklyThreshold = 3
   * the maximum number of transactions a TradingUser can be involved in per calendar week 
* incompleteThreshold = 2
   * the maximum number of incomplete transactions that can occur per TradingUser


Types of Transactions:
* Permanent (One meeting involved)
   * One-Way: TradingUser1 permanently gives an Item to TradingUser2
   * Two-Way: Two TradingUsers permanently give two Items to each other
* Temporary (Two meetings involved)
   * One-Way: TradingUser1 lends an Item to TradingUser2 at the first meeting, then TradingUser2 returns the Item at the second meeting
   * Two-Way: Two TradingUsers lend an Item to each other at the first meeting, then both TradingUsers return those Items back to each other at the second meeting
   *** FOR TEMPORARY TRANSACTIONS: after the second meeting occurs, the items automatically returns to the respective user's Inventory. If the user no longer wants to trade this item, it must be removed manually

Transaction Thresholds:
* editThreshold = 3
   * the maximum number of edits (per TradingUser) per Transaction can be done before the Transaction is automatically cancelled


Exceptions:
* InvalidAdminException checks if a AdminUsers corresponds to a valid AdminUser during login  
* InvalidItemException checks if an Item is valid
* InvalidTradingUserException checks if the TradingUser corresponds to a valid TradingUser
* InvalidTransactionException checks if Transaction id maps to a Transaction 


What’s Being Saved
* admins.ser contains a list of all AdminUsers
* users.ser contains a list of all TradingUsers
* items.ser contains a map of a UUID to the Item corresponding to it
* transactions.ser contains a map of UUID to the Transaction corresponding to it
* flaggedAccounts.ser contains a list of all flagged TradingUsers
* frozenAccounts.ser contains a list of all frozen TradingUsers
* requestedItems.ser contains a map of requested Item to the TradingUser who requested it


Packages
* Admins package contains the classes that specifically pertain to Admins. This includes: AdminUser, AdminManager, AdminMenuController, AdminMenuPresenter.
* Exceptions package contains the Exceptions that are unique to the TradingSystem software. This includes: InvalidAdminException, InvalidItemException, InvalidTradingUserException, InvalidTransactionException.
* Items package contains the classes that specifically pertain to Items. This includes: Item, ItemManager.
* Presenters package contains classes that are general presenter classes. This includes: BootUpMenuPresenter and MenuPresenter
* Transactions package contain the classes that specifically pertains to the handling of transactions: This includes: Transaction, TransactionManager, TransactionOneWayPerm, TransactionOneWayTemp, TransactionTwoWayTemp, TransactionTwoWayPerm, CurrentTransactionManager, PastTransactionManager, Meeting 
* Users package contains the classes that pertain to Users. This includes: User, TradingUser, TradingUserManager, TransactionHistory, UserMenuController, and UserMenuPresenter


Assumptions
* Different users of the TradingApplication program must either use the program on the same computer or use a repository that stores the system to operate on the same program
* TradingApplication must log out PROPERLY after each use. Otherwise, information will not be serialized and will be lost 
* There is one master admin user which can create all other admins in the system. The username is: admin, and the password: password to access this account
* In Transactions, user1 always creates the transaction whereas user2 has the item that user1 desires
* TradingUser accounts are flagged only when they perform an action past a specific limitation. For example, if a user reaches their weekly transaction limit, they will be flagged when the number of their weekly transactions EXCEEDS the weekly transaction limit


UML 
* The UML diagram is colour coded based on clean architecture
   * Pink: Entity Classes
   * Yellow: Use Case Classes
   * Blue: Presenter, Controller, Gateway Classes


Authors (alphabetical)
Casey Hon, Annie Liu, Christian Mitrache, Anna Shirkalina, Brandon Tiu, Tingting Marina Zhang