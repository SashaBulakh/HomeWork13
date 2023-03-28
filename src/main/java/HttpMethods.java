import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpMethods {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    static int x;
    static int y;

    public static User sendPost(URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        final HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendGetByName(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = GSON.fromJson(response.body(), JsonArray.class);
        User user = null;
        for (JsonElement element : array) {
            user = GSON.fromJson(element, User.class);
        }
        return user;
    }

    public static User sendGetById(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User UpdateRequest(URI uri, User user) throws IOException, InterruptedException {
        final String updateBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(updateBody)).build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static int DeleteRequest(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static List<User> AllInfoRequest(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        return GSON.fromJson(response.body(), type);
    }

    public static UsersPosts GetMaxId(URI uri) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<UsersPosts>>() {}.getType();
        List<UsersPosts> usersPostsList = GSON.fromJson(response.body(), type);
        UsersPosts maxIdUser = usersPostsList.stream().max(Comparator.comparing(UsersPosts::getId)).orElse(null);

        assert maxIdUser != null;
        x = maxIdUser.getUserId();
        y = maxIdUser.getId();

        return maxIdUser;

    }

    public static List<UsersComments> UsersComments(URI uri) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<UsersComments>>() {}.getType();
        List<UsersComments> usersCommentsList = GSON.fromJson(response.body(), type);
        File file = new File(String.format("user-%d-post-%d-comments.json", x, y));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String g = gson.toJson(usersCommentsList);
        OutputStream fos = Files.newOutputStream(Path.of(String.valueOf(file)));
        fos.write(g.getBytes());
        return usersCommentsList;

    }

    public static List<UsersOpenTasks> SortByActiveStatus(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<UsersOpenTasks>>() {}.getType();
        List<UsersOpenTasks> users = GSON.fromJson(response.body(), type);
        Stream<UsersOpenTasks> activeTasks = users.stream().filter(n -> !n.isCompleted());
        return activeTasks.collect(Collectors.toList());
    }
}