package mk.ukim.finki.wp.lab.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Artist {
    Long id;
    String firstName;
    String lastName;
    String bio;

    public Artist(long id, String n, String ln, String b) {
        this.id = id;
        this.firstName = n;
        this.lastName = ln;
        this.bio = b;
    }


}
