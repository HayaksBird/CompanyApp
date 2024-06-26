# Employee Management Application


## Description
This is an employee management system app which allows:
* **to share data management with other workers.** Now the presure of managing the data is not on the database admin alone, since the app users (managers) can update/create/delete other workers as well.
* **workers to communicate with each other.** Workers can add & read posts and thus be aware of the latests news.
* **workers to see relevant to them information.** The workers can view their personal info and their department info and thus have a better sense ofunderstanding of their position and their role in the company.

## Roles
The app is comprised of 3 roles of workers - manager, senior, junior. The role hierarchy is linear and works in inheriting (accumulative) fashion. Meaning, each role is eather beneath or above the other role, and if it is above, then it will have all the access levels of the roles beneath it.

* **Junior** - lowest role by rank. A junior worker can only view a limitted amount of info on their department and view the posting activity.
* **Senior** - goes after junior. A senior worker can in addition to junior's abillities create posts (notifications) for other workers of the company. They can manage the lifespan of that post or even update it.
* **Manager** - goes after senior. A manager has the ability to add/remove/update workers of their own corresponding department. Besides that, they can view the additional statistics info of their department.

I have also decided to add the ability for the database admin to perform their work through the app interface. The admin has the maximum authority level. They can manage anything within any department, and they can fully manage the posting activity. 

## App Security
I have implemented a 2 layer security system for this application.
**First (Authentication)** is the registration/login process, where the program checks whether the user is present in the database (via worker id for the registration or the username for the login). Thus, in order for the worker to use the application, they must already exist in the system.
**Second (Authorization)** is the token system for the further interaction. After the registration/login a JWT is generated as a contract between the end user and the server. This token will have a lifespan of 1 day. Thus, after the authentication a user can freely wonder around the website for a day, before they need to authenticate again.

## Details
The biggest portion of this project was spent not on the realization of concepts, but on code efficiency.
I tried to make the code as modular as the common sense allows it to.
Thus, with the help of already existing annotations, enum roles, and java reflection used in combination it is very easy to add new worker types, update the data for the existing, or even create new roles. The annotations in combination with java validation mechanisms allow one to enforce restrictions and rules for any worker type in just couple lines of code.
Much of the code I have written was about creating unit blocks from which one can build different "shapes" and utilize the already existing tools.
If this project were to be expanded further and new people were to be invited to work on it, then it would be quite simple for them to orient around due to a tree-like structure of the project, where application internals, secuirity, validation, etc have their corresponding "domes" with further separation of concerns within each one.
