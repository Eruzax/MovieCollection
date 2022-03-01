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

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
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
    ArrayList<String> actors = new ArrayList<>();
    System.out.println("Enter a person to search for (first or last name: ");
    String word = scanner.nextLine().toLowerCase();
    int counter = 1;
    for (int i = 0; i < movies.size(); i++)
    {
      String[] actor = movies.get(i).getCast().split("\\|");
      for (String cast : actor)
      {
        actors.add(cast);
      }
    }
    for (int j = 1; j < actors.size(); j++)
    {
      String temp = actors.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(actors.get(possibleIndex - 1)) < 0)
      {
        actors.set(possibleIndex, actors.get(possibleIndex - 1));
        possibleIndex--;
      }
      actors.set(possibleIndex, temp);
    }
    ArrayList<String>
    for (int j = 0; j < actors.size(); j++)
    {
      if (actors.get(j).toLowerCase().contains(word))
      {

      }
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
  
  private void listGenres()
  {
    /* TASK 5: IMPLEMENT ME! */
  }
  
  private void listHighestRated()
  {
    /* TASK 6: IMPLEMENT ME! */
  }
  
  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
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
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
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

        // create Cereal object to store values
       Movie nextMovie = new Movie(title, cast, director, tagLine, keywords, overview, runtime, genres, rating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}