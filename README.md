# Sjakk
Sjakkspillet er laget på bakgrunn av dette rammeverket, hvor unødvendige klasser og andre spill er fjernet:

## Brettspill-rammeverk
Dette repoet inneholder et rammeverk for brettspill. Det ble brukt som semesteroppgave 2 i årene 2020 - 2022.
Du finner allerede fire brettspill implementert:
 * TicTacToe
 * Connect4
 * Othello
 * BlobWars

Rammeverket har også en AI-algoritme/spiller som fungerer likt for alle spillene.

## Om sjakkspillet
Spillet kan startes gjennom å kjøre MainChessGUI.java som finnes i inf101.chess.main.
Regler fra vanlig sjakkspill gjelder utenom disse spesialreglene:
 * En passant: Spesialtrekket en passant er ikke implementert.
 * Three-fold repition: Det er ikke automatisk remis om en og samme posisjon gjentas tre ganger.
 * Fifty-move rule: Det er ikke automatisk remis om det spilles 50 trekk uten at noen brikker blir tatt eller bønder flyttes.
 
 Grunnen til at disse ikke er implementert er både av tidsmessige årsaker, og at de er nokså perifere i forhold til
 mer viktige regler som rokade, bondepromotering og reglene om sjakk/sjakkmatt/patt.
 
 Spillet kan spilles med to spillere, men også med en AI som brukes Alpha-Beta algoritmen. På grunn av skjevheter i 
 evaluasjonsmetoden (som var utenom prosjektets skop å perfeksjonere denne gang)
 fungerer ikke denne AI-en godt pr. nå på dybde over 3, så det anbefales å velge denne dybden.
 Høyere dybder bruker også veldig lang tenketid, særlig i starten av spillet der mange trekk er mulig.
 Dybde-3 versjonen spiller overraskende greit, og tar vanligvis en brikke om en har muligheten til det.
 
## Om prosjektets oppbygging
Prosjektet er delt inn i pakker som representerer hver sin del av spillet:
 * Logic: Logikken ved rokade, når kongen er i sjakk og validering samt innhenting av trekk er håndtert her.
 * Model: Brettmodellen og en basic spillmodel (som utvides i main for å integrere flere spillelementer) kan finnes her.
 * Pieces: En abstract brikkeklasse og alle sjakkbrikkene er representert i denne pakken.
 * Player: Har ulike typer spillere og spillerlisten.
 * Player.AI: Her er unike AI-spillere om relevante klasser som de bruker.
 * View: Alt som har med grafikk finnes i denne pakken.
 * Grid: Grid-klassen og Lokasjoner er i denne klassen sammen med Move og ChessMove som utnytter Lokasjoner.
 * Main: Programmet som samler spillet sammen og er ansvarlig for game-loopen samt programmet som starter menyen finnes her.
 
## Om testing
Alle brikkene (noen gjennom en test av DirectionalCalculator), brettet, spillet og logikken er testet her.
Grafikk er testet gjennom praksis, da jeg fant ut at dette var en mer passende fremgangsmåte på grafikk for å
få representasjonen helt riktig. AI-spillere var også mer passende å teste ut i praksis. 
 
## Video
 
 En video med demo av spillet er lagt ut på denne YouTube linken:
  *