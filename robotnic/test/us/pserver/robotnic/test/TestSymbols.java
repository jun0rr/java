/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import us.pserver.robotnic.Key;
import us.pserver.robotnic.Keyboard;
import us.pserver.robotnic.RepeatingCombo;
import us.pserver.robotnic.Robotnic;
import us.pserver.robotnic.ScriptCombo;


/**
 *
 * @author juno
 */
public class TestSymbols {
  
  //'&*@\[]çÇ^:,©{}°÷$=€!
  //½¼¾`>«<-±×—()%.|+£'"®»;#/ ¹²³↓←→↑ ~™_
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    r.delay(2000);
    ScriptCombo nl = ScriptCombo.of(Keyboard.ENTER, RepeatingCombo.of(Key.SLASH, 2));
    ScriptCombo symbols = ScriptCombo.of(
        Keyboard.ACUTE,
        Keyboard.AMPERSAND,
        Keyboard.ASTERISC,
        Keyboard.AT,
        Keyboard.BACKSLASH,
        Keyboard.BRACELEFT,
        Keyboard.BRACERIGHT,
        Keyboard.CCEDIL_LOWER,
        Keyboard.CCEDIL_UPPER,
        Keyboard.CIRC,
        Keyboard.COLON,
        Keyboard.COMMA,
        Keyboard.COPYRIGHT,
        Keyboard.CURLY_BRACE_LEFT,
        Keyboard.CURLY_BRACE_RIGHT,
        Keyboard.DEGREES,
        Keyboard.DIVISION,
        Keyboard.DOLLAR,
        Keyboard.EQUALS,
        Keyboard.EUR,
        Keyboard.EXCLAMATION
    );
    symbols.accept(r);
    nl.accept(r);
    ScriptCombo symbols2 = ScriptCombo.of(
        Keyboard.FRACTION_1_2,
        Keyboard.FRACTION_1_4,
        Keyboard.FRACTION_3_4,
        Keyboard.GRAVE,
        Keyboard.GREATER,
        Keyboard.LEFTDBL,
        Keyboard.LESSER,
        Keyboard.MINUS,
        Keyboard.MORE_OR_LESS,
        Keyboard.MULTIPLY,
        Keyboard.PARAGRAPH,
        Keyboard.PARENTHESIS_LEFT,
        Keyboard.PARENTHESIS_RIGHT,
        Keyboard.PERCENT,
        Keyboard.PERIOD,
        Keyboard.PIPE,
        Keyboard.PLUS,
        Keyboard.POUND,
        Keyboard.QUOTE,
        Keyboard.QUOTEDBL,
        Keyboard.REGISTERED,
        Keyboard.RIGHTDBL,
        Keyboard.SEMICOLON,
        Keyboard.SHARP,
        Keyboard.SLASH,
        Keyboard.SPACE,
        Keyboard.SUPER_1,
        Keyboard.SUPER_2,
        Keyboard.SUPER_3,
        Keyboard.SYMBOL_DOWN,
        Keyboard.SYMBOL_LEFT,
        Keyboard.SYMBOL_RIGHT,
        Keyboard.SYMBOL_UP,
        Keyboard.TAB,
        Keyboard.TILDE,
        Keyboard.TM,
        Keyboard.UNDERSCORE
    );
    symbols2.accept(r);
  }
  
}
