package vn.cusc.examm2;

/**
 * Created by Admin on 03/07/2017.
 */

public class Animal {
    int id;
    String name;
    String image;
    String voice;

    public Animal(int id, String name, String image, String voice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.voice = voice;
    }

    public Animal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
}
