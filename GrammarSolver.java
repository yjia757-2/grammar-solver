// Yiran Jia
// 5/3/18
// CSE143
// TA: JASON WAATAJA 
// Assignment 5: Grammar Solver
//
// This program help the user randomly generates elements of the grammar. Once the 
// user choose one of the exist rules, it will randomly generate content that is 
// only made up by the terminal symbols. This program will generate the given 
// number of occurrences of the results.  

import java.util.*;

public class GrammarSolver {
   private SortedMap<String, String> collect;

   // pre: this grammar is not empty and there is at most one entry in the
   // grammar for the same nonterminal (throw an IllegalArgumentException if not)
   // post: construct a map that stores the rules from the grammar as its keys, 
   // and the content of the rule from the grammar as its values. 
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      collect = new TreeMap<String, String>();
      for (String each : grammar) { 
         String[] parts = each.split("::=");
         if (grammarContains(parts[0])) {
            throw new IllegalArgumentException();
         }
         collect.put(parts[0], parts[1]);
      }     
   } 
    
   // post: return if the given symbol is a nonterminal of the grammar
   public boolean grammarContains(String symbol) {
      return collect.containsKey(symbol);
   }
   
   // pre: the grammar does contain the given nonterminal symbol and 
   // the number of times is at least 0 
   // (throw new IllegalArgumentException if not) 
   // post: use the grammar to randomly generate the given number of occurrences 
   // of the given symbol. It returns the result as an array of strings.
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] answer = new String[times]; 
      for (int i = 0; i < times; i++) {
         answer[i] = sentence(symbol).trim();
      } 
      return answer;
   }
  
   // post: return a string that is the result of the given rule. It chooses 
   // a rule from the map based on the given rule, and randomly picks a new rule or 
   // symbol from this rule's pool. If it picks a rule, it will generate whatever that 
   // new rule tells it to generate. 
   private String sentence(String rule) {
      String result = "";
      if (!grammarContains(rule)) {
         result += rule + " ";
      } else {
         String startPt = collect.get(rule);
         String[] tools = startPt.split("[|]");
         int randy = (int) (Math.random()*(tools.length));
         String choose = tools[randy].trim();
      
         if (!choose.contains(" ") && !grammarContains(choose)) {    
            result += choose + " ";
         } else {
            String[] tools2 = choose.split("[ \t]+");
            for (String each : tools2) {
               result += sentence(each);
            }
         }
      }
      return result;
   }
   
   // post: return a string representation of the various nonterminal symbols 
   // from the grammar as a sorted, comma-separated list enclosed in square brackets. 
   public String getSymbols() { 
      return collect.keySet().toString();
   } 
}
      
   