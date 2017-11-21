import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestApi {
    public static final String apiKey = "e968d24a995806e222b5f7240e0c9a6b";
    public static final String base_url = "http://image.tmdb.org/t/p/";
    public static final String search_url = "https://api.themoviedb.org/3/search/multi?query=";
    public static String size = "w300";

    public JSONObject makeRequest(String url) {
        JSONObject jsonObject = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                jsonObject = new JSONObject(response.toString());
                System.err.println("MakeRequest: Udalo sie.");
            } else {
                System.err.println("MakeRequest: nie jest HTTP_OK.");
            }
        } catch (IOException | JSONException e) {
            //e.printStackTrace();
            System.err.println("Blad w makeRequest. Prawdopodobnie nie ma netu.");

        }
        return jsonObject;
    }


    public ArrayList<HomeElement> getDiscover(String address) {
        ArrayList<HomeElement> elements = new ArrayList<>();
        JSONObject discover;
        try {
            discover = makeRequest(address + "&api_key=" + apiKey);
            if(discover == null) throw new NullPointerException("Pusto w discover.");
        } catch (NullPointerException e) {
            elements.add(new HomeElement("","Brak połączenia z netem.",1));
            return elements;
        }

        try {
            JSONArray array = discover.getJSONArray("results");
            for(int i = 0; i < 10; i++) {
                JSONObject element = (JSONObject) array.get(i);
                String label;
                try {
                    label = element.getString("title");
                } catch (JSONException o) {
                    label = element.getString("name");
                }
                String imgUrl = element.getString("poster_path");
                int id = element.getInt("id");
                HomeElement homeElement = new HomeElement(imgUrl, label, id);
                elements.add(homeElement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Bład w discoverMovies");
        }

        return elements;
    }

    public ArrayList<SearchElement> getSearchResults(String key) {
        ArrayList<SearchElement> elements = new ArrayList<>();

        JSONObject search = makeRequest(search_url + key + "&language=pl-PL&api_key=" + apiKey);

        try {
            int totalResults = Integer.parseInt(search.getString("total_results"));
            System.err.println("Total results: " + totalResults);
            JSONArray array = search.getJSONArray("results");

            for(int i = 0; i<20; i++) {
                JSONObject element = (JSONObject) array.get(i);
                String name;
                try {
                    name = element.getString("title");
                } catch (JSONException o) {
                    name = element.getString("name");
                }
                String date;
                try {
                    date = element.getString("first_air_date");
                } catch (JSONException o) {
                    try {
                        date = element.getString("release_date");
                    } catch (JSONException jo) {
                        date = "-";
                    }
                }
                String mediaType = element.getString("media_type");
                int id = element.getInt("id");
                double vote;
                try {
                    vote = element.getDouble("vote_average");
                } catch (JSONException o) {
                    vote = 0.0;
                }
                String imgUrl;
                try {
                    imgUrl = element.getString("poster_path");
                    if(imgUrl.equals("")) throw new JSONException("No poster_path.");
                } catch (JSONException o) {
                    System.err.println("Brak pola poster_path.");
                    imgUrl = "";
                }
                JSONArray genreIds;
                int[] genres = new int[1];
                try {
                    genreIds = element.getJSONArray("genre_ids");
                    genres = new int[genreIds.length()];
                    for (int j = 0; j < genreIds.length(); j++) {
                        genres[j] = genreIds.getInt(j);
                    }
                } catch (JSONException o) {
                    System.err.println("Brak pola gatunki.");
                }

                SearchElement searchElement = new SearchElement(name, date, genres, mediaType, vote, id, imgUrl);
                elements.add(searchElement);
            }

        } catch (JSONException e) {
            System.err.println("Koniec wynikow.");
        }
        return elements;
    }
}
