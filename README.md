# Company Application
This is an employee management system app which allows:
* **to share data management with other workers.** Now the presure of managing the data is not on the database admin alone, since the app users (managers) can update/create/delete other workers as well.
* **workers to communicate with each other.** Workers can add & read posts and thus be aware of the latests news.
* **workers to see relevant to them information.** The workers can view their personal info and their department info and thus have a better sense ofunderstanding of their position and their role in the company.

The app is comprised of 3 roles of workers - manager, senior, junior. The role hierarchy is linear and works in inheriting (accumulative) fashion. Meaning, each role is eather beneath or above the other role, and if it is above, then it will have all the access levels of the roles beneath it.

* **Junior** - lowest role by rank. A junior worker can only view a limitted amount of info on their department and view the posting activity.
* **Senior** - goes after junior. A senior worker can in addition to junior's abillities create posts (notifications) for other workers of the company. They can manage the lifespan of that post or even update it.
* **Manager** - goes after senior. A manager has the ability to add/remove/update workers of their own corresponding department. Besides that, they can view the additional statistics info of their department.

I have also decided to add the ability for the database admin to perform their work through the app interface. The admin has the maximum authority level. They can manage anything within any department, and they can fully manage the posting activity. 

## App Security
