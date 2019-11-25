package edu.ktu.labaratorywork1;

import java.util.ArrayList;

public class DancesData {
    public DancesData(){}

    public ArrayList<ListItem> createBasicDances(){
        ArrayList<ListItem> allDances = new ArrayList<>();
        allDances.add(new ListItem("Slow Waltz", R.drawable.danceballroom,
                "Like all Standard category dances, Waltz is a progressive dance," +
                        "meaning that dancers travel along a path known as the line of dance. " +
                        "It is characterized by pendulum swing movements and incorporates general " +
                        "elements of ballroom technique such as foot parallelism, rise and fall," +
                        "contra body movement and sway.","YB0xhCD47Zk"));
        allDances.add(new ListItem("Tango", R.drawable.danceballroom,
                "Initially, the English dominated the International style tango, but eventually," +
                        "technicians from other backgrounds, most notably the Italians," +
                        "have chipped away at the English standard and created a dynamic" +
                        "style that continues to raise the competitive bar.",
                "qNRp4PSANwg"));
        allDances.add(new ListItem("Slow Foxtrot", R.drawable.danceballroom,
                "Most figures are based upon four-count units with the rhythm slow " +
                        "(two counts), quick (one count), quick (one count), repeating in each measure. " +
                        "A basic dance sequence progressing around the room might employ" +
                        "a feather step (four counts), reverse turn with feather finish (eight counts), " +
                        "three step (four counts), natural turn (four counts) with impetus (four count)" +
                        "and feather finish (four count), connecting again to a reverse turn.",
                "K5B5-WmjjSU"));
        allDances.add(new ListItem("Viennese Waltz", R.drawable.danceballroom,
                "The Viennese waltz is a rotary dance where the dancers are constantly " +
                        "turning either in a clockwise (\"natural\") or anti-clockwise (\"reverse\") " +
                        "direction interspersed with non-rotating change steps to switch between " +
                        "the direction of rotation. A true Viennese waltz consists only of turns " +
                        "and change steps. Other moves such as the fleckerls, American-style figures" +
                        "and side sway or underarm turns are modern inventions.",
                "c8ivQ5qM-uA"));
        allDances.add(new ListItem("Quickstep", R.drawable.danceballroom,
                "Its origins are in combination of slow foxtrot combined with the Charleston, " +
                        "a dance which was one of the precursors to what today is called swing dancing.",
                "dnNu1tSRYg8"));

        allDances.add(new ListItem("Cha cha cha", R.drawable.dancelatin,
                "In the international Latin style, the weighted leg is almost always straight. " +
                        "The free leg will bend, allowing the hips to naturally settle into the direction of " +
                        "the weighted leg. As a step is taken, a free leg will straighten the instant " +
                        "before it receives weight. It should then remain straight until it is completely " +
                        "free of weight again.", "BrlCDonGXZY"));
        allDances.add(new ListItem("Rumba", R.drawable.dancelatin,
                "The international ballroom rumba is a slower dance of about 120 beats per minute " +
                        "which corresponds, both in music and in dance, to what the Cubans of an " +
                        "older generation called the bolero-son. ", "0zBy6x6mD6g"));
        allDances.add(new ListItem("Samba", R.drawable.dancelatin,
                "Most steps are danced with a slight downward bouncing or dropping action. " +
                        "This action is created through the bending and straightening of the knees, " +
                        "with bending occurring on the beats of 1 and 2, and the straightening occurring between.",
                "5mCbjfU31BI"));
        allDances.add(new ListItem("Pasodoble", R.drawable.dancelatin,
                " Highlights emphasize music and are more powerful sounding than the other " +
                        "parts of the music. Usually, at the highlights, dancers perform a trick " +
                        "and a position that that they hold to the end of the highlight.",
                "JvAWgX8e76g"));
        allDances.add(new ListItem("Jive", R.drawable.dancelatin,
                "Today The Jive is one of the five International Latin dances. In competition " +
                        "it is danced at a speed of 176 beats per minute, although in some cases " +
                        "this is reduced to between 128 and 160 beats per minute.",
                "0EPz0QgJxjU"));

        allDances.add(new ListItem("Salsa", R.drawable.dancesolo,
                "In many styles of salsa dancing, as a dancer shifts their weight by " +
                        "stepping, the upper body remains level and nearly unaffected by the " +
                        "weight changes. Weight shifts cause the hips to move.",
                "wt7f_Bu2OE8"));
        allDances.add(new ListItem("Bachata", R.drawable.dancesolo,
                "In partnering, the lead can decide whether to perform in open, " +
                        "semi-closed or closed position. Dance moves or step variety strongly " +
                        "depend on the music (such as the rhythms played by the different instruments), " +
                        "setting, mood, and interpretation. Unlike salsa, bachata dance does not " +
                        "usually include many turn patterns.", "U-JofUEsbD0"));
        allDances.add(new ListItem("Argentine tango", R.drawable.dancesolo,
                "Argentine tango dancing consists of a variety of styles that developed " +
                        "in different regions and eras, and in response to the crowding of the " +
                        "venue and even the fashions in clothing.",
                "Gcs4LY_ljQk"));
        return allDances;
    }

    public ArrayList<ListItem> createDanceTypes(){
        ArrayList<ListItem> danceTypes = new ArrayList<>();
        danceTypes.add(new ListItem("Ballroom", R.drawable.danceballroom,"Slow Waltz, Tango, Slow Foxtrot, Viennese Waltz, QuickStep"));
        danceTypes.add(new ListItem("Latin", R.drawable.dancelatin,"Cha cha cha, Rumba, Samba, Pasodoble, Jive"));
        danceTypes.add(new ListItem("Social dance", R.drawable.dancelatin,"Salsa, Bachata, Argentine tango"));
        danceTypes.add(new ListItem("Solo dance", R.drawable.dancesolo,"Salsa, Bachata, Argentine tango"));
        return danceTypes;
    }
}
