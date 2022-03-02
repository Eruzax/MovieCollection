import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    switch (option)
    {
      case "t" -> searchTitles();
      case "c" -> searchCast();
      case "k" -> searchKeywords();
      case "g" -> listGenres();
      case "r" -> listHighestRated();
      case "h" -> listHighestRevenue();
      default -> System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();


    searchTerm = searchTerm.toLowerCase();


    ArrayList<Movie> results = new ArrayList<Movie>();


    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {

        results.add(movies.get(i));
      }
    }


    sortResults(results);


    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();


      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter the name of the actor/actress: ");
    String searchTerm = scanner.nextLine();


    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> castList = new ArrayList<String>();
    for (int i = 0; i < movies.size(); i++)
    {
      String[] cast = movies.get(i).getCast().split("\\|");
      for (String members : cast)
      {
        if (!(castList.contains(members)))
        {
          castList.add(members);
        }
      }
    }

    for (int j = 1; j < castList.size(); j++)
    {
      String temp = castList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(castList.get(possibleIndex - 1)) < 0) {
        castList.set(possibleIndex, castList.get(possibleIndex - 1));
        possibleIndex--;
      }
      castList.set(possibleIndex, temp);

      ArrayList<String> results = new ArrayList<String>();
      for (String actor : castList)
      {
        if (actor.toLowerCase().contains(searchTerm.toLowerCase())) {
          results.add(actor);
        }
      }

      for (int i = 0; i < results.size(); i++) {

        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + results.get(i));
      }

      System.out.println("Which cast member?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();

      ArrayList<Movie> movieResults = new ArrayList<Movie>();
      String actor = results.get(choice - 1);
      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getCast().contains(actor)) {
          movieResults.add(movies.get(i));
        }
      }
      for (int i = 0; i < movieResults.size(); i++) {
        String title = movieResults.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      int selection = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = movieResults.get(selection - 1);

      displayMovieInfo(selectedMovie);

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchKeywords()
  {
    ArrayList<Movie> movieList = new ArrayList<>();
    System.out.println("Enter a keyword search term: ");
    String word = scanner.nextLine();
    int counter = 1;
    for(int i = 0; i < movies.size(); i++)
    {
      if(movies.get(i).getKeywords().contains(word.toLowerCase()))
      {
        movieList.add(movies.get(i));
      }
    }
    sortResults(movieList);
    for(int j = 0; j < movieList.size(); j++)
    {
      System.out.println(counter + ". " + movieList.get(j).getTitle());
      counter++;
    }
    System.out.println("Which movie would you like to learn more about?\nEnterNumber: ");
    int choice = scanner.nextInt();
    displayMovieInfo(movieList.get(choice));
  }
  
  private void listGenres() {
    ArrayList<String> genreList = new ArrayList<String>();
    for (int i = 0; i < movies.size(); i++) {
      String[] genres = movies.get(i).getGenres().split("\\|");
      for (String genre : genres)
      {
        if (!(genreList.contains(genre)))
        {
          genreList.add(genre);
        }

      }
    }

    for (int j = 1; j < genreList.size(); j++)
    {
      String temp = genreList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(genreList.get(possibleIndex - 1)) < 0) {
        genreList.set(possibleIndex, genreList.get(possibleIndex - 1));
        possibleIndex--;
      }
      genreList.set(possibleIndex, temp);


      for (int i = 0; i < genreList.size(); i++) {
        String genre = genreList.get(i);

        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + genre);
      }

      System.out.println("Which genre?");
      System.out.print("Enter number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      ArrayList<Movie> movieResults = new ArrayList<Movie>();
      String selectedGenre = genreList.get(choice - 1);
      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getGenres().contains(selectedGenre))
        {
          movieResults.add(movies.get(i));
        }
      }
      sortResults(movieResults);
      for (int i = 0; i < movieResults.size(); i++) {
        String title = movieResults.get(i).getTitle();


        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      int selection = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = movieResults.get(selection - 1);

      displayMovieInfo(selectedMovie);

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();

    }
  }
  private void listHighestRated()
  {
    ArrayList<Movie> highestRated = movies;
    for (int i = 0; i < highestRated.size(); i++)
    {
      int minIndex = i;
      for (int j = i + 1; j < highestRated.size() - 1; j++)
      {
        if (highestRated.get(j).getUserRating() > highestRated.get(minIndex).getUserRating())
        {
          minIndex = j;
        }
      }
      Movie temp = highestRated.get(i);
      highestRated.set(i, highestRated.get(minIndex));
      highestRated.set(minIndex, temp);
    }

    ArrayList<Movie> top50 = new ArrayList<Movie>();
    for (int i = 0; i < 50; i++)
    {
      top50.add(highestRated.get(i));
    }

    for (int i = 0; i < top50.size(); i++)
    {
      String title = top50.get(i).getTitle();
      double rating = top50.get(i).getUserRating();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + " " + rating);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int selection = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50.get(selection - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> highestRevenue = movies;
    for (int i = 0; i < highestRevenue.size(); i++)
    {
      int minIndex = i;
      for (int j = i + 1; j < highestRevenue.size() - 1; j++)
      {
        if (highestRevenue.get(j).getRevenue() > highestRevenue.get(minIndex).getRevenue())
        {
          minIndex = j;
        }
      }
      Movie temp = highestRevenue.get(i);
      highestRevenue.set(i, highestRevenue.get(minIndex));
      highestRevenue.set(minIndex, temp);
    }

    ArrayList<Movie> top50 = new ArrayList<Movie>();
    for (int i = 0; i < 50; i++)
    {
      top50.add(highestRevenue.get(i));
    }

    for (int i = 0; i < top50.size(); i++)
    {
      String title = top50.get(i).getTitle();
      int revenue = top50.get(i).getRevenue();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + " : $" + revenue);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int selection = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50.get(selection - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {

        String[] moviesFromCSV = line.split(",");


        String title = moviesFromCSV[0];
        String cast = moviesFromCSV[1];
        String director = moviesFromCSV[2];
        String tagLine = moviesFromCSV[3];
        String keywords = moviesFromCSV[4];
        String overview = moviesFromCSV[5];
        int runtime = Integer.parseInt(moviesFromCSV[6]);
        String genres = moviesFromCSV[7];
        double rating = Double.parseDouble(moviesFromCSV[8]);
        int year = Integer.parseInt(moviesFromCSV[9]);
        int revenue = Integer.parseInt(moviesFromCSV[10]);


       Movie nextMovie = new Movie(title, cast, director, tagLine, keywords, overview, runtime, genres, rating, year, revenue);


        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {

      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  


}