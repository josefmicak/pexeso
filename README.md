# Pexeso for Android
Pexeso (also known as "Memory game") for Android. There are 8 pairs of cards in the grid, during each move the player tries to find a matching pair of cards. The objective of the game is to finish the game in the least amount of moves possible.

Features:
- the player's best scores will be stored in the device
- the general best scores of all players will be available online
- design customization (the user will be able to change the game's theme color as well as the card pictures)
- sounds (can be disabled by the user)

TAMZ II - obsažené funkce
- Advanced GUI - využití Lists, Tabs
- Concurrency - využití separátních vláken pro síťovou komunikaci
- Database - práce s nejlepšími výsledky hráče s využitím databáze
- Multimedia - přehrávání zvuků při určitých událostech
- Geo Location - zjištění uživatelovy zeměpisné lokace na úrovní státu za účelem případného zpracování jeho skóre
- Persistent Storage - ukládání vybraného uživatelova vzhledu aplikace a sady obrázků používaných během hry do SharedPreferences
- Networking - práce s nejlepšími výsledky všech hráčů, čtení a úprava souboru uloženého na externím serveru pomocí PHP
