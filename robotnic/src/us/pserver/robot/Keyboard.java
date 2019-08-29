/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;

import java.awt.event.KeyEvent;


/**
 *
 * @author juno
 */
public enum Keyboard implements Key {
  
  A(KeyEvent.VK_A, 'a'),
  
  ALT(KeyEvent.VK_ALT, "ALT"),
  ALT_GRAPH(KeyEvent.VK_ALT_GRAPH, "ALTGR"),
  AMPERSAND(KeyEvent.VK_AMPERSAND, '&'),
  ARROW_DOWN(KeyEvent.VK_KP_DOWN, "DOWN"),
  ARROW_LEFT(KeyEvent.VK_KP_LEFT, "LEFT"),
  ARROW_RIGHT(KeyEvent.VK_KP_RIGHT, "RIGHT"),
  ARROW_UP(KeyEvent.VK_KP_UP, "UP"),
  ASTERISC(KeyEvent.VK_ASTERISK, '*'),
  AT(KeyEvent.VK_AT, '@'),
  
  BACKSLASH(KeyEvent.VK_BACK_SLASH, '\\'),
  BACKSPACE(KeyEvent.VK_BACK_SPACE, "BACKSPACE"),
  BRACELEFT(KeyEvent.VK_BRACELEFT, '['),
  BRACERIGHT(KeyEvent.VK_BRACERIGHT, ']'),
  
  C(KeyEvent.VK_C, 'c'),
  
  CAPSLOCK(KeyEvent.VK_CAPS_LOCK, "CAPSLOCK"),
  CLOSE_BRACKET(KeyEvent.VK_CLOSE_BRACKET, ']'),
  CLOSE_PARENTHESIS(KeyEvent.VK_RIGHT_PARENTHESIS, ')'),
  COLON(KeyEvent.VK_COLON, ':'),
  COMMA(KeyEvent.VK_COMMA, ','),
  CTRL(KeyEvent.VK_CONTROL, "CTRL"),
  CCEDIL(KeyEvent.VK_DEAD_CEDILLA, 'ç'),
  
  D(KeyEvent.VK_D, 'd'),
  
  DELETE(KeyEvent.VK_DELETE, "DEL"),
  DOLLAR(KeyEvent.VK_DOLLAR, '$'),
  
  E(KeyEvent.VK_E, 'e'),
  
  END(KeyEvent.VK_END, "END"),
  ENTER(KeyEvent.VK_ENTER, "ENTER"),
  EQUALS(KeyEvent.VK_EQUALS, '='),
  ESCAPE(KeyEvent.VK_ESCAPE, '?'),
  EXCLAMATION(KeyEvent.VK_EXCLAMATION_MARK, '!'),
  
  F(KeyEvent.VK_F, 'f'),
  
  F1(KeyEvent.VK_F1, "F1"),
  F2(KeyEvent.VK_F2, "F2"),
  F3(KeyEvent.VK_F3, "F3"),
  F4(KeyEvent.VK_F4, "F4"),
  F5(KeyEvent.VK_F5, "F5"),
  F6(KeyEvent.VK_F6, "F6"),
  F7(KeyEvent.VK_F7, "F7"),
  F8(KeyEvent.VK_F8, "F8"),
  F9(KeyEvent.VK_F9, "F9"),
  F10(KeyEvent.VK_F10, "F10"),
  F11(KeyEvent.VK_F11, "F11"),
  F12(KeyEvent.VK_F12, "F12"),
  
  G(KeyEvent.VK_G, 'g'),
  
  GREATER(KeyEvent.VK_GREATER, '>'),
  
  H(KeyEvent.VK_H, 'h'),
  
  HOME(KeyEvent.VK_HOME, "HOME"),
  
  I(KeyEvent.VK_I, 'i'),
  
  INSERT(KeyEvent.VK_INSERT, 'i'),
  
  J(KeyEvent.VK_J, 'j'),
  K(KeyEvent.VK_K, 'k'),
  
  L(KeyEvent.VK_L, 'l'),
  M(KeyEvent.VK_M, 'm'),
  
  MINUS(KeyEvent.VK_MINUS, '-'),
  
  N(KeyEvent.VK_N, 'n'),
  NUM_LOCK(KeyEvent.VK_NUM_LOCK, "NUM_LOCK"),
  
  N0(KeyEvent.VK_0, '0'),
  N1(KeyEvent.VK_1, '1'),
  N2(KeyEvent.VK_2, '2'),
  N3(KeyEvent.VK_3, '3'),
  N4(KeyEvent.VK_4, '4'),
  N5(KeyEvent.VK_5, '5'),
  N6(KeyEvent.VK_6, '6'),
  N7(KeyEvent.VK_7, '7'),
  N8(KeyEvent.VK_8, '8'),
  N9(KeyEvent.VK_9, '9'),
  
  O(KeyEvent.VK_O, 'o'),
  
  OPEN_BRACKET(KeyEvent.VK_OPEN_BRACKET, '['),
  OPEN_PARENTHESIS(KeyEvent.VK_OPEN_BRACKET, '['),
  
  P(KeyEvent.VK_P, 'p'),
  
  PG_DOWN(KeyEvent.VK_PAGE_DOWN, "PG_DOWN"),
  PG_UP(KeyEvent.VK_PAGE_UP, "PG_UP"),
  PERIOD(KeyEvent.VK_PERIOD, '.'),
  PLUS(KeyEvent.VK_PLUS, '+'),
  PRINTSCREEN(KeyEvent.VK_PRINTSCREEN, "PRINTSCREEN"),
  
  Q(KeyEvent.VK_Q, 'q'),
  
  QUOTE(KeyEvent.VK_QUOTE, '\''),
  QUOTEDBL(KeyEvent.VK_QUOTEDBL, '"'),
  
  R(KeyEvent.VK_R, 'r'),
  S(KeyEvent.VK_S, 's'),
  
  SCROLL_LOCK(KeyEvent.VK_SCROLL_LOCK, "SCROLL_LOCK"),
  SEMICOLON(KeyEvent.VK_SEMICOLON, ';'),
  SHIFT(KeyEvent.VK_SHIFT, "SHIFT"),
  SLASH(KeyEvent.VK_SLASH, '/'),
  SPACE(KeyEvent.VK_SPACE, ' '),
  
  T(KeyEvent.VK_T, 't'),
  
  TAB(KeyEvent.VK_TAB, "TAB"),
  
  U(KeyEvent.VK_U, 'u'),
  
  UNDERSCORE(KeyEvent.VK_UNDERSCORE, 'u'),
  
  V(KeyEvent.VK_V, 'v'),
  W(KeyEvent.VK_W, 'w'),
  
  WIN(KeyEvent.VK_WINDOWS, "WIN"),
  
  X(KeyEvent.VK_X, 'x'),
  Y(KeyEvent.VK_Y, 'y'),
  Z(KeyEvent.VK_Z, 'z'),
  ;
  
  private Keyboard(int cd, char ch) {
    this.code = cd;
    this.ch = ch;
    this.str = Character.toString(ch);
  }
  
  private Keyboard(int cd, String str) {
    this.code = cd;
    this.ch = '?';
    this.str = str;
  }
  
  private final int code;
  
  private final char ch;
  
  private final String str;
  
  @Override
  public int getCode() {
    return code;
  }
  
  @Override
  public char getChar() {
    return ch;
  }
  
  @Override
  public String toString() {
    return str;
  }
  
}
