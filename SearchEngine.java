import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/search")) {
            String[] searchParameters = url.getQuery().split("=");
            String returnString = "";
            for (int i = 0; i < strList.size(); i++) {
                if(searchParameters[0].equals("s")) {
                    if (strList.get(i).contains(searchParameters[1])) {
                        if (i < (strList.size()-1)) {
                            returnString += strList.get(i) + ", ";
                        } else if (i == (strList.size()-1)) {
                            returnString += strList.get(i);
                        }
                    }
                }
                else {return "404 Not Found";}
            }
            return String.format("Results: %s", returnString);
        } else if (url.getPath().contains("/add")) {
                String[] stringParameters = url.getQuery().split("=");
                if (stringParameters[0].equals("s")) {
                    strList.add(stringParameters[1]);
                    return String.format("String %s was added!", stringParameters[1]);
                }
                return "404 Not Found!";
        } else { return "404 Not Found"; }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
