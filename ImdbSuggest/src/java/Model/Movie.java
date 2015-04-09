/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author KK
 */
/**
 * A POJO fro holding details about the movie
 */
public class Movie {
private String id;
private String title;
private int year;
private String thumb;
private String link ="";
private String suggestedFor = "";


/**
 * constructor
 */
public Movie() {
	
}
/**
 *  constructor
 * @param id
 * @param title
 * @param year
 */
public Movie(String id, String title, int year) {
	super();
	this.id = id;
	this.title = title;
	this.year = year;
}
/**
 *  get Id
 * @return
 */
public String getId() {
	return id;
}
/**
 * setId
 * @param id
 */
public void setId(String id) {
	this.id = id;
}
/**
 * get Title
 * @return
 */
public String getTitle() {
	return title;
}
/**
 * set Title
 * @param title
 */
public void setTitle(String title) {
	this.title = title;
}
/**
 * get year
 * @return
 */
public int getYear() {
	return year;
}
/**
 * set year
 * @param year
 */
public void setYear(int year) {
	this.year = year;
}

/**
 * get thumbnail
 * @return
 */
public String getThumb() {
	return thumb;
}
/**
 * set Thumbnail
 * @param thumb
 */
public void setThumb(String thumb) {
	this.thumb = thumb;
}



/**
 * get url
 * @return
 */
public String getLink() {
	return link;
}
/**
 * set url
 * @param link
 */
public void setLink(String link) {
	this.link = link;
}

    public String getSuggestedFor() {
        return suggestedFor;
    }

    public void setSuggestedFor(String suggestedFor) {
        this.suggestedFor = suggestedFor;
    }




@Override
public String toString() {
	return "Movie [id=" + id + ", title=" + title + ", year=" + year
			+ ", thumb=" + thumb + ", link=" + link +"]";
}


}
