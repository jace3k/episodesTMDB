public class FavsRefresher {
    private static Controller controller;

    public static void setController(Controller c) {
        controller = c;
    }

    public static void refresh() {
        controller.refreshFavs();
    }

}
