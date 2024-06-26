# shot-blasting-machines-group-2

## Description
Projekt stanowi aplikację dla firmy produkującej śrutownice. Aplikacja wspiera firmę w procesie produkcji i sprzedaży swoich produktów

## Przepis i strategia firmy
Aplikacja pozwala na tworzenie, zarządzanie produkcją oraz dystrybucję śrutownic. Zamówienia tworzone są przez dział sprzedaży, po wcześniejszej personalizacji ich przez klienta. Następnie zamówienia przetwarzane są przez dział produkcji, który wykonuje proces tworzenia śrutownic. Po skończonej produkcji, każdy egzemplarz zostaje poddany testom jakościowym oraz funkcjonalnym. Po zakończonych testach śrutownica wraca do działu sprzedaży, skąd zostaje wysłana do klienta.

## Dane techniczne
Dzięki zastosowaniu narzędzia Electron aplikacja będzie uruchamiać się jako aplikacja desktopowa. Zarządzanie procesem powinno być tak zooptymalizowane, aby wszystkie elementy działały płynnie i szybko. Interjest powinien być intuicyjny oraz przejrzysty.

## Wymagania funkcjonalne
- Dodawanie nowych śrutownic to oferty
- Edycja i usuwanie śrutownic
- Zarządzanie magazynem
- Personalizacja zamówień przez klientów
- Przyjmowanie zamówień
- Tworzenie śrutownic
- Zarządzanie procesem produkcji
- Zarządzaniem procesem sprzedaży
- Zarządzanie testami
- Finalizacja zamówień
- Generowanie faktur oraz listy elementów do wykonania

## Wymagania niefunkcjonalne
- Aplikacja nawiązuje połączenie z bazą danych i używa rekordów w niej zapisanych
- Możliwość dodawania, usuwania oraz edycji rekordów w bazie z poziomu pracownika
- Aplikacja tworzona jest w języku Java z frameworkiem Spring Boot
- 
## Diagram przypadków użycia
![](/assets/DPU.png)

### Definicje aktorów
<b>Aktor:</b> Pracownik produkcji<br/>
<b>Opis:</b> Zarządzanie produkcją śrutownic<br/>
<b>Przypadki Użycia:<br/>
- PU Aktualizacja statusu produkcji<br/>
- PU Weryfikowanie jakości innych pracowników produkcji<br/>
- PU Generowanie raportu maszyny do budowy<br/>
<br/>

<b>Aktor:</b> Administrator systemu<br/>
<b>Opis:</b> Obsługa całości systemu oraz współpraca z innymi użytkownikami aplikacji<br/>
<b>Przypadki Użycia:<br/>
- PU Zarządzanie systemem<br/>
- PU Zarządzanie użytkownikami<br/>
- PU Zarządzanie rolami użytkownikow powiązane przez <<extend>> z PU Zarządzanie użytkownikami<br/>

<b>Aktor:</b> Pracownik działu sprzedaży<br/>
<b>Opis:</b> Obsługa sprzedaży śrutownic, konsultacje z klientami oraz organizacja wysyłek produktów<br/>
<b>Przypadki Użycia:<br/>
- PU Przyjmowanie zamówień<br/>
- PU Finalizacja zamówień<br/>
- PU Personalizacja produktu powiązane przez <<include>> z PU Przyjmowanie produktu<br/>
- PU Wysyłka zamówień powiązane przez <<include>> z PU Finalizacja zamówień<br/>
</br>

<b>Aktor:</b> Nadzorca magazynu<br/>
<b>Opis:</b> Obsługa magazynu, odpowiedzialność za zamawianie materiałów, kontrola jakości stanu magazynowego<br/>
<b>Przypadki Użycia:<br/>
- PU Zamawianie materiałów do magazynu<br/>
- PU Określenie dyspozycyjności magazynu do zamówienia<br/>
<br/>

<b>Aktor:</b> Konstruktor maszyn<br/>
<b>Opis:</b> Wytwarzanie nowych śrytownic<br/>
<b>Przypadki Użycia:<br/>
- PU Dodowanie maszyn do oferty<br/>
- PU Wprowadzenie materiałow do produkcji przez <<include>> z PU Dodawanie maszyn do oferty<br/>

## Diagramy aktywności
![](/assets/ActivityDiagram_I.png)
![](/assets/ActivityDiagram_II.png)
![](/assets/ActivityDiagram_III.png)
![](/assets/ActivityDiagram_IV.png)
![](/assets/ActivityDiagram_V.png)

## GUI
![](/assets/profile_GUI.PNG)
![](/assets/users_GUI.PNG)
![](/assets/orders_GUI.PNG)
![](/assets/production_GUI.PNG)

## Wykorzystane technologie
- Electron
- Java
- Spring Boot
- MySQL
- Hibernate
- TypeScript
- Vue.js
- Gitlab
- Jira
- Intellij

