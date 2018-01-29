import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestApi {
    public static final String apiKey = "e968d24a995806e222b5f7240e0c9a6b";
    public static final String base_url = "http://image.tmdb.org/t/p/";
    public static final String search_url = "https://api.themoviedb.org/3/search/multi?query=";
    public static final String movie_url = "https://api.themoviedb.org/3/movie/";
    public static final String tv_url = "https://api.themoviedb.org/3/tv/";
    public static String size = "w300";

    private static JSONObject makeRequest(String url) {
        JSONObject jsonObject = new JSONObject();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            JSONObject jsonResponse = getJSON(con.getInputStream());
            jsonObject.put("response", jsonResponse);
            jsonObject.put("response_code", responseCode);
            Lo.g("Response code: " + responseCode);
        } catch (IOException | JSONException e) {
            System.err.println("Nie można ustanowić połączenia");
            try {
                jsonObject.put("response", new JSONObject().put("status_message", "Problem z połączeniem"));
                jsonObject.put("response_code", 0);

            } catch (JSONException e1) {
                System.err.println("Nie można dodać jsona");
            }
        }
        return jsonObject;
    }

    private static JSONObject getJSON(InputStream stream) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new JSONObject(response.toString());
    }

    public static ArrayList<HomeElement> discover(String url, boolean isMovie) {
        ArrayList<HomeElement> elements = new ArrayList<>();
        JSONObject discoverJSON = makeRequest(url + "&api_key=" + apiKey);
        try {
            if (discoverJSON.getInt("response_code") == 200) {
                for(int i = 0; i < 10; i++) {
                    JSONObject result = discoverJSON.getJSONObject("response").getJSONArray("results").getJSONObject(i);
                    String title = "title";
                    if (result.has("name")) title = "name";
                    elements.add(new HomeElement(result.getString("poster_path"), result.getString(title), result.getInt("id"), isMovie));
                }
            } else {
                elements.add(new HomeElement("", discoverJSON.getJSONObject("response").getString("status_message"), 0, isMovie));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return elements;
    }

    public static ArrayList<Object> search(String key, int page) {
        ArrayList<Object> elementsAndTotalPages = new ArrayList<>();
        ArrayList<SearchElement> elements = new ArrayList<>();
        JSONObject searchJSON = makeRequest(search_url + key + "&language=pl-PL&page=" + page + "&api_key=" + apiKey);

        int totalPages = 1;
        try {
            if (searchJSON.getInt("response_code") == 200) {
                JSONArray results = searchJSON.getJSONObject("response").getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);

                    String name = "No name", date = "", mediaType = "", poster = "";
                    double vote = 0;
                    int id = 0;

                    if (result.has("name")) name = result.getString("name");
                    if (result.has("title")) name = result.getString("title");

                    if (result.has("release_date")) date = result.getString("release_date");
                    if (result.has("first_air_date")) date = result.getString("first_air_date");

                    if (result.has("media_type")) mediaType = result.getString("media_type");

                    if (result.has("poster_path")) poster = result.getString("poster_path");

                    if (result.has("vote_average")) vote = result.getDouble("vote_average");

                    if (result.has("id")) id = result.getInt("id");


                    elements.add(new SearchElement(name, date, mediaType, vote, id, poster));
                }
                if (results.length() == 0) elements.add(new SearchElement("Nic nie znaleziono"));
                totalPages = searchJSON.getJSONObject("response").getInt("total_pages");
            } else {
                elements.add(new SearchElement(searchJSON.getJSONObject("response").getString("status_message")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        elementsAndTotalPages.add(elements);
        elementsAndTotalPages.add(totalPages);
        return elementsAndTotalPages;
    }

    public static JSONObject getMovieDetails(int id) {
        return makeRequest(movie_url + id + "?api_key=" + apiKey + "&language=pl-PL");
    }

    public static JSONObject getTVDetails(int id) {
        return makeRequest(tv_url + id + "?api_key=" + apiKey + "&language=pl-PL");
    }

    public static JSONObject getSeasonDetails(int id, int seasonNo) {
        return makeRequest(tv_url + id + "/season/" + seasonNo + "?api_key=" + apiKey + "&language=pl-PL");
    }
}
