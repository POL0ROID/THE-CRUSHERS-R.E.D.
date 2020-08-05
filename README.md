# THE-CRUSHERS-R.E.D.
Database program and vote interface for administering ranked-choice elections.

Ranked Choice Election Database v0.9.9
by THE CRUSHERS (Emma Yanoviak and William Baker "Polo Sepulveda")

The program is started in Main.java, and the console will prompt for:

-a password,

-vote instructions,

-and a message after voting

from the election administrator.

After, the vote view will appear.

It will be populated with the same candidates data used to populate part of the model,

which can be configured in "Candidates.txt".

The vote controller will store voter inputs, and when they are finalized,

put them in the model too, and call the password view.

The password controller will check the input password against the administrator's password,

and if they match, allow the user to go back to put in another vote,

or continue to the results.

The results view displays the model's data as a bar graph,

and has a button to go to the next round.

The last candidate remaining is the victor.

Known (significant) bugs:

-Vote instructions and after-voting message do not display in this version

-The bar graph is not populated

-The round 2+ algorithm does not function

It is also possible the model is being stored or read incorrectly somewhere.
