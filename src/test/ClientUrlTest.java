// **********************************************************
// Assignment2:
// Student1: Christian Chen Liu
// UTORID user_name: Chenl147
// UT Student #: 1006171009
// Author: Christian Chen Liu
//
// Student2: Christopher Suh
// UTORID user_name: suhchris
// UT Student #: 1006003664
// Author: Christopher Suh
//
// Student3: Andrew D'Amario
// UTORID user_name: damario4
// UT Student #: 1006618947
// Author: Andrew D'Amario
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ClientUrl;

import commands.*;
import data.Cache;
import data.FileSystem;
import driver.JShell;
import io.Parser;
import io.StandardInput;
import runtime.Execution;

public class ClientUrlTest {

  private ClientUrl curl;
  private JShell shell;
  private String expectedFile =
  "There was once a king who had an illness, and no one believed that he\r\n"
  + "would come out of it with his life.  He had three sons who were much\r\n"
  + "distressed about it, and went down into the palace-garden and wept.\r\n"
  + "There they met an old man who inquired as to the cause of their\r\n"
  + "grief.  They told him that their father was so ill that he would most\r\n"
  + "certainly die, for nothing seemed to cure him.  Then the old man\r\n"
  + "said, I know of one more remedy, and that is the water of life.  If\r\n"
  + "he drinks of it he will become well again, but it is hard to find.\r\n"
  + "The eldest said, I will manage to find it. And went to the sick\r\n"
  + "king, and begged to be allowed to go forth in search of the water of\r\n"
  + "life, for that alone could save him.  No, said the king, the\r\n"
  + "danger of it is too great.  I would rather die.\r\n" + "\r\n"
  + "But he begged so long that the king consented.  The prince thought in\r\n"
  + "his heart, If I bring the water, then I shall be best beloved of my\r\n"
  + "father, and shall inherit the kingdom. So he set out, and when he\r\n"
  + "had ridden forth a little distance, a dwarf stood there in the road\r\n"
  + "who called to him and said, Whither away so fast? Silly shrimp,\r\n"
  + "said the prince, very haughtily, it is nothing to do with you. And\r\n"
  + "rode on.  But the little dwarf had grown angry, and had wished an\r\n"
  + "evil wish.  Soon after this the prince entered a ravine, and the\r\n"
  + "further he rode the closer the mountains drew together, and at last\r\n"
  + "the road became so narrow that he could not advance a step further.\r\n"
  + "It was impossible either to turn his horse or to dismount from the\r\n"
  + "saddle, and he was shut in there as if in prison.  The sick king\r\n"
  + "waited long for him, but he came not.\r\n" + "\r\n"
  + "Then the second son said, father, let me go forth to seek the\r\n"
  + "water. And thought to himself, If my brother is dead, then the\r\n"
  + "kingdom will fall to me. At first the king would not allow him to go\r\n"
  + "either, but at last he yielded, so the prince set out on the same\r\n"
  + "road that his brother had taken, and he too met the dwarf, who\r\n"
  + "stopped him to ask whither he was going in such haste.  Little\r\n"
  + "shrimp, said the prince, that is nothing to do with you. And rode\r\n"
  + "on without giving him another look.  But the dwarf bewitched him, and\r\n"
  + "he, like the other, rode into a ravine, and could neither go forwards\r\n"
  + "nor backwards.  So fare haughty people.\r\n" + "\r\n"
  + "As the second son also remained away, the youngest begged to be\r\n"
  + "allowed to go forth to fetch the water, and at last the king was\r\n"
  + "obliged to let him go.  When he met the dwarf and the latter asked\r\n"
  + "him whither he was going in such haste, he stopped, gave him an\r\n"
  + "explanation, and said, I am seeking the water of life, for my father\r\n"
  + "is sick unto death.\r\n" + "\r\n"
  + "Do you know, then, where that is to be found?\r\n" + "\r\n"
  + "No, said the prince.\r\n" + "\r\n"
  + "As you have borne yourself as is seemly, and not haughtily like your\r\n"
  + "false brothers, I will give you the information and tell you how you\r\n"
  + "may obtain the water of life.  It springs from a fountain in the\r\n"
  + "courtyard of an enchanted castle, but you will not be able to make\r\n"
  + "your way to it, if I do not give you an iron wand and two small\r\n"
  + "loaves of bread.  Strike thrice with the wand on the iron door of the\r\n"
  + "castle and it will spring open, inside lie two lions with gaping\r\n"
  + "jaws, but if you throw a loaf to each of them, they will be quieted.\r\n"
  + "Then hasten to fetch some of the water of life before the clock\r\n"
  + "strikes twelve else the door will shut again, and you will be\r\n"
  + "imprisoned.\r\n" + "\r\n"
  + "The prince thanked him, took the wand and the bread, and set out on\r\n"
  + "his way.  When he arrived, everything was as the dwarf had said.  The\r\n"
  + "door sprang open at the third stroke of the wand, and when he had\r\n"
  + "appeased the lions with the bread, he entered the castle, and came to\r\n"
  + "a large and splendid hall, wherein sat some enchanted princes whose\r\n"
  + "rings he drew off their fingers.  A sword and a loaf of bread were\r\n"
  + "lying there, which he carried away.  After this, he entered a\r\n"
  + "chamber, in which was a beautiful maiden who rejoiced when she saw\r\n"
  + "him, kissed him, and told him that he had set her free, and should\r\n"
  + "have the whole of her kingdom, and that if he would return in a year\r\n"
  + "their wedding should be celebrated.  Likewise she told him where the\r\n"
  + "spring of the water of life was, and that he was to hasten and draw\r\n"
  + "some of it before the clock struck twelve.  Then he went onwards, and\r\n"
  + "at last entered a room where there was a beautiful newly-made bed,\r\n"
  + "and as he was very weary, he felt inclined to rest a little.  So he\r\n"
  + "lay down and fell asleep.\r\n" + "\r\n"
  + "When he awoke, it was striking a quarter to twelve.  He sprang up in\r\n"
  + "a fright, ran to the spring, drew some water in a cup which stood\r\n"
  + "near, and hastened away.  But just as he was passing through the iron\r\n"
  + "door, the clock struck twelve, and the door fell to with such\r\n"
  + "violence that it carried away a piece of his heel.\r\n" + "\r\n"
  + "He, however, rejoicing at having obtained the water of life, went\r\n"
  + "homewards, and again passed the dwarf.  When the latter saw the sword\r\n"
  + "and the loaf, he said, With these you have won great wealth, with\r\n"
  + "the sword you can slay whole armies, and the bread will never come to\r\n"
  + "an end. But the prince would not go home to his father without his\r\n"
  + "brothers, and said, Dear dwarf, can you not tell me where my two\r\n"
  + "brothers are?  They went out before I did in search of the water of\r\n"
  + "life, and have not returned.\r\n" + "\r\n"
  + "They are imprisoned between two mountains, said the dwarf. I have\r\n"
  + "condemned them to stay there, because they were so haughty. Then the\r\n"
  + "prince begged until the dwarf released them, but he warned him and\r\n"
  + "said, Beware of them, for they have bad hearts. When his brothers\r\n"
  + "came, he rejoiced, and told them how things had gone with him, that\r\n"
  + "he had found the water of life and had brought a cupful away with\r\n"
  + "him, and had rescued a beautiful princess, who was willing to wait a\r\n"
  + "year for him, and then their wedding was to be celebrated and he\r\n"
  + "would obtain a great kingdom.\r\n" + "\r\n"
  + "After that they rode on together, and chanced upon a land where war\r\n"
  + "and famine reigned, and the king already thought he must perish, for\r\n"
  + "the scarcity was so great.  Then the prince went to him and gave him\r\n"
  + "the loaf, wherewith he fed and satisfied the whole of his kingdom,\r\n"
  + "and then the prince gave him the sword also wherewith he slew the\r\n"
  + "hosts of his enemies, and could now live in rest and peace.  The\r\n"
  + "prince then took back his loaf and his sword, and the three brothers\r\n"
  + "rode on.  But after this they entered two more countries where war\r\n"
  + "and famine reigned and each time the prince gave his loaf and his\r\n"
  + "sword to the kings, and had now delivered three kingdoms, and after\r\n"
  + "that they went on board a ship and sailed over the sea.  During the\r\n"
  + "passage, the two eldest conversed apart and said, The youngest has\r\n"
  + "found the water of life and not we, for that our father will give him\r\n"
  + "the kingdom - the kingdom which belongs to us, and he will rob us of\r\n"
  + "all our fortune. They then began to seek revenge, and plotted with\r\n"
  + "each other to destroy him.  They waited until they found him fast\r\n"
  + "asleep, then they poured the water of life out of the cup, and took\r\n"
  + "it for themselves, but into the cup they poured salt sea-water.\r\n"
  + "\r\n"
  + "Now therefore, when they arrived home, the youngest took his cup to\r\n"
  + "the sick king in order that he might drink out of it, and be cured.\r\n"
  + "But scarcely had he drunk a very little of the salt sea-water than he\r\n"
  + "became still worse than before.  And as he was lamenting over this,\r\n"
  + "the two eldest brothers came, and accused the youngest of having\r\n"
  + "intended to poison him, and said that they had brought him the true\r\n"
  + "water of life, and handed it to him.  He had scarcely tasted it, when\r\n"
  + "he felt his sickness departing, and became strong and healthy as in\r\n"
  + "the days of his youth.\r\n" + "\r\n"
  + "After that they both went to the youngest, mocked him, and said, You\r\n"
  + "certainly found the water of life, but you have had the pain, and we\r\n"
  + "the gain, you should have been cleverer, and should have kept your\r\n"
  + "eyes open.  We took it from you whilst you were asleep at sea, and\r\n"
  + "when a year is over, one of us will go and fetch the beautiful\r\n"
  + "princess. But beware that you do not disclose aught of this to our\r\n"
  + "father, indeed he does not trust you, and if you say a single word,\r\n"
  + "you shall lose your life into the bargain, but if you keep silent,\r\n"
  + "you shall have it as a gift.\r\n" + "\r\n"
  + "The old king was angry with his youngest son, and thought he had\r\n"
  + "plotted against his life.  So he summoned the court together and had\r\n"
  + "sentence pronounced upon his son, that he should be secretly shot.\r\n"
  + "And once when the prince was riding forth to the chase, suspecting no\r\n"
  + "evil, the king's huntsman was told to go with him, and when they were\r\n"
  + "quite alone in the forest, the huntsman looked so sorrowful that the\r\n"
  + "prince said to him, Dear huntsman, what ails you? The huntsman\r\n"
  + "said, I cannot tell you, and yet I ought. Then the prince said,\r\n"
  + "Say openly what it is, I will pardon you. Alas, said the\r\n"
  + "huntsman, I am to shoot you dead, the king has ordered me to do it.\r\n"
  + "Then the prince was shocked, and said, Dear huntsman, let me live,\r\n"
  + "there, I give you my royal garments, give me your common ones in\r\n"
  + "their stead. The huntsman said, I will willingly do that, indeed I\r\n"
  + "would not have been able to shoot you. Then they exchanged clothes,\r\n"
  + "and the huntsman returned home, while the prince went further into\r\n"
  + "the forest.\r\n" + "\r\n"
  + "After a time three waggons of gold and precious stones came to the\r\n"
  + "king for his youngest son, which were sent by the three kings who had\r\n"
  + "slain their enemies with the prince's sword, and maintained their\r\n"
  + "people with his bread, and who wished to show their gratitude for it.\r\n"
  + "The old king then thought, Can my son have been innocent? And said\r\n"
  + "to his people, Would that he were still alive, how it grieves me\r\n"
  + "that I have suffered him to be killed. He still lives, said the\r\n"
  + "huntsman, I could not find it in my heart to carry out your\r\n"
  + "command. And told the king how it had happened.  Then a stone fell\r\n"
  + "from the king's heart, and he had it proclaimed in every country that\r\n"
  + "his son might return and be taken into favor again.\r\n" + "\r\n"
  + "The princess, however, had a road made up to her palace which was\r\n"
  + "quite bright and golden, and told her people that whosoever came\r\n"
  + "riding straight along it to her, would be the right one and was to be\r\n"
  + "admitted, and whoever rode by the side of it, was not the right one\r\n"
  + "and was not to be admitted.\r\n" + "\r\n"
  + "As the time was now close at hand, the eldest thought he would hasten\r\n"
  + "to go to the king's daughter, and give himself out as her rescuer,\r\n"
  + "and thus win her for his bride, and the kingdom to boot.  Therefore\r\n"
  + "he rode forth, and when he arrived in front of the palace, and saw\r\n"
  + "the splendid golden road, he thought, it would be a sin and a shame\r\n"
  + "if I were to ride over that.  And turned aside, and rode on the right\r\n"
  + "side of it. But when he came to the door, the servants told him that\r\n"
  + "he was not the right one, and was to go away again.\r\n" + "\r\n"
  + "Soon after this the second prince set out, and when he came to the\r\n"
  + "golden road, and his horse had put one foot on it, he thought, it\r\n"
  + "would be a sin and a shame, a piece might be trodden off.  And he\r\n"
  + "turned aside and rode on the left side of it, and when he reached the\r\n"
  + "door, the attendants told him he was not the right one, and he was to\r\n"
  + "go away again.\r\n" + "\r\n"
  + "When at last the year had entirely expired, the third son likewise\r\n"
  + "wished to ride out of the forest to his beloved, and with her forget\r\n"
  + "his sorrows.  So he set out and thought of her so incessantly, and\r\n"
  + "wished to be with her so much, that he never noticed the golden road\r\n"
  + "at all.  So his horse rode onwards up the middle of it, and when he\r\n"
  + "came to the door, it was opened and the princess received him with\r\n"
  + "joy, and said he was her saviour, and lord of the kingdom, and their\r\n"
  + "wedding was celebrated with great rejoicing.  When it was over she\r\n"
  + "told him that his father invited him to come to him, and had forgiven\r\n"
  + "him.\r\n" + "\r\n"
  + "So he rode thither, and told him everything, how his brothers had\r\n"
  + "betrayed him, and how he had nevertheless kept silence.  The old king\r\n"
  + "wished to punish them, but they had put to sea, and never came back\r\n"
  + "as long as they lived.\r\n";

  @Before
  public void setUp() throws Exception {
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem());

  }

  @After
  public void tearDown() throws Exception {
    Field field =
        (shell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  // Check if done correctly for txt files and also checks for invalid links
  @Test
  public void correctlyRetrievesFileTest1() throws Exception {
    curl = new ClientUrl();
    setUp();

    expectedFile = expectedFile.replaceAll("[ \r\n]+", "");

    String[] actual = {"curl", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};
    curl.run(actual, shell);
    assertNotNull(
      "curl not correctly creating/naming file in the current directory",
      shell.getfSystem().getCurrentDirectory().getDirectory().
      getFileByFileName("073txt"));
    assertEquals("curl did not retrieve correct contents of file", expectedFile,
      shell.getfSystem().getCurrentDirectory().getDirectory().
      getFileByFileName("073txt").getContent()
        .replaceAll("[ \r\n]+", ""));
    assertNotNull("curl must return error for invalid command arguments",
      curl.run(actual, shell).getErrors());
    String[] actual2 = {"curl", "dasa"};
    assertEquals("curl must return error for invalid link",
      "curl: \"dasa\": Invalid valid URL or file found",
      curl.run(actual2, shell).getErrors());
    tearDown();
  }

  // Checks if done correctly for html files
  @Test
  public void correctlyRetrievesFileTest2() throws Exception {
    curl = new ClientUrl();
    setUp();
    String expected = "<script language='javascript'> var timeZone ='%2B0800';"
      + " var dstbox =''; var cpageBgColor = 'FFFFFF'; var wordAd=''; var "
      + "wadurl='http://'; var width='100%'; var tableFontSize='15'; var "
      + "cborderColor='FFCCFF'; var ctdColor1='FFFFFF'; var ctdColor2='FFFFF"
      + "F'"
      + "; var clinkColor='990099'; var cdateFontColor='990099'; var cdateB"
      + "gCo"
      + "lor='FBF0FB'; var scoreFontSize='15'; var cteamFontColor='000000';"
      + " var cgoalFontColor='FF0000'; var cgoalBgColor='FFE1FF'; var crema"
      + "rkFontColor='000000'; var mark = 'vn'; var cremarkBgColor='F7F8F3';"
      + " var Skins='6'; var teamWeight='400'; var scoreWeight='700'; var go"
      + "alWeight='700'; var fontWeight='700'; var voi ='0'; var away ='0'; "
      + "var red ='0'; document.write(<iframe src='//freelive.7m.com.cn/liv"
      + "e.aspx?mark=+ mark +&TimeZone= + timeZone + &wordAd= + wordAd + &"
      + "cpageBgColor=+ cpageBgColor +&wadurl= + wadurl + &width= + width "
      + "+ &tableFontSize= + tableFontSize + &cborderColor= + cborderColor "
      + "+ &ctdColor1= + ctdColor1 + &ctdColor2= + ctdColor2 + &clinkColor="
      + " + clinkColor + &cdateFontColor=+ cdateFontColor +&cdateBgColor= +"
      + " cdateBgColor + &scoreFontSize= + scoreFontSize + &cteamFontColor="
      + " + cteamFontColor + &cgoalFontColor= + cgoalFontColor + &cgoalBgCol"
      + "or= + cgoalBgColor + &cremarkFontColor= + cremarkFontColor + &cre"
      + "markBgColor= + cremarkBgColor + &Skins= + Skins + &teamWeight= + "
      + "teamWeight + &scoreWeight= + scoreWeight + &goalWeight= + goalW"
      + "eight +&fontWeight=+ fontWeight +&DSTbox=+ dstbox +&voi=+ voi +&"
      + "away=+ away +&red=+ red +'  height='100%' width='100%' scrolling='"
      + "yes' border='0' frameborder='0'></iframe>)</script>\r\n" + "";

    expected = expected.replaceAll("[ \r\n]+", "");

    String[] actual = {"curl", "https://kk996.net/football.html"};
    curl.run(actual, shell);
    assertNotNull(
      "curl not correctly creating/naming file in the current directory",
      shell.getfSystem().getCurrentDirectory().getDirectory().
      getFileByFileName("footballhtml"));
    assertEquals("curl did not retrieve correct contents of file", expected,
      shell.getfSystem().getCurrentDirectory().getDirectory().
      getFileByFileName("footballhtml")
        .getContent().replaceAll("[ \r\n]+", ""));
    tearDown();
  }
}
