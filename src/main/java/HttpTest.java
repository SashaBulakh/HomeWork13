import java.io.IOException;
import java.net.URI;
import java.util.List;


public class HttpTest {
    private static final String ALL_USER = "https://jsonplaceholder.typicode.com/users";
    private static final String NEW_USER_CREATE = "https://jsonplaceholder.typicode.com/users";
    private static final String GET_USER_BY_ID = "https://jsonplaceholder.typicode.com/users/1";
    private static final String UPDATE_USER = "https://jsonplaceholder.typicode.com/users/10";
    private static final String GET_USER_BY_USERNAME = "https://jsonplaceholder.typicode.com/users?username=Bret";
    private static final String DELETE_USER = "https://jsonplaceholder.typicode.com/users/6";
    private static final String MAX_ID_USER = "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String COMMENTS = "https://jsonplaceholder.typicode.com/posts/10/comments";
    private static final String OPEN_TASKS = "https://jsonplaceholder.typicode.com/users/1/todos";


    public static void main(String[] args) throws IOException, InterruptedException {

        User user = new User();
        Address address = new Address();
        Geo geo = new Geo();
        Company company = new Company();


        user.setId(11);
        user.setName("Leanne Graham");
        user.setUsername("Moriah.Stanton");
        user.setEmail("Rey.Padberg@karina.biz");
        user.setAddress(address);
        address.setStreet("Kattie Turnpike");
        address.setSuite("Suite 198");
        address.setCity("Lebsackbury");
        address.setZipcode("31428-2261");
        address.setGeo(geo);
        geo.setLat("-38.2386");
        geo.setLng("57.2232");
        user.setPhone("024-648-3804");
        user.setWebsite("ambrose.net");
        user.setCompany(company);
        company.setName("Hoeger LLC");
        company.setCatchPhrase("Centralized empowering task-force");
        company.setBs("target end-to-end models");

        //TASK #1.1
        final User newUser = HttpMethods.sendPost(URI.create(NEW_USER_CREATE), user);
        System.out.println(newUser);

        user.setName("Leanne");
        user.setUsername("Moriah");
        user.setEmail("Padberg@karina.biz");
        user.setAddress(address);
        address.setStreet("Kattie");
        address.setSuite("Suite");
        address.setCity("Lebsackbury");
        address.setZipcode("2261");
        address.setGeo(geo);
        geo.setLat("-38.286");
        geo.setLng("57.232");
        user.setPhone("024-3804");
        user.setWebsite("ambrose.net");
        user.setCompany(company);
        company.setName("Hoeger LLC");
        company.setCatchPhrase("Centralized empowering task-force");
        company.setBs("target end-to-end models");

        //TASK #1.2
        final User updateUser = HttpMethods.UpdateRequest(URI.create(UPDATE_USER), user);
        System.out.println(updateUser);

        //TASK#1.3
        final int delete = HttpMethods.DeleteRequest(URI.create(DELETE_USER));
        System.out.println("Status code " + delete);

        //TASK#1.4
        final List<User> allInfo = HttpMethods.AllInfoRequest(URI.create(ALL_USER));
        System.out.println(allInfo);

        //TASK #1.5
        final User byID = HttpMethods.sendGetById(URI.create(GET_USER_BY_ID));
        System.out.println(byID);

        //TASK#1.6
        final User byUserName = HttpMethods.sendGetByName(URI.create(GET_USER_BY_USERNAME));
        System.out.println(byUserName);

        //TASK#2
        final UsersPosts MaxIdUser = HttpMethods.GetMaxId(URI.create(MAX_ID_USER));
        System.out.println(MaxIdUser);

        final List<UsersComments> byMaxId = HttpMethods.UsersComments(URI.create(COMMENTS));
        System.out.println(byMaxId);

        //TASK#3
        final List<UsersOpenTasks> activeTasks = HttpMethods.SortByActiveStatus(URI.create(OPEN_TASKS));
        System.out.println(activeTasks.toString());
    }
}
