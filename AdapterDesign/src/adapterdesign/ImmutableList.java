/**
 * 
 * Adam Maser
 * CSC422
 * AdapterDesign Project
 * 11/17/19
 * 
 */
package adapterdesign;

import java.util.ArrayList;

/**
 *
 * @author adammaser
 */
public class ImmutableList implements IImmutableList {
    public ArrayList<Integer> imList = new ArrayList<>();
    
    //int array constructor
    public ImmutableList(int[] list) {
        for (int i = 0; i < list.length; i++) {
            this.imList.add(list[i]);
        }
    };
    
    //IImmutableList constructor
    public ImmutableList(IImmutableList list) {
        for (int i = 0; i < list.size(); i++) {
            this.imList.add(list.get(i));
        }
    }
    
    /**
    * Returns the element at position index
    * @param index the index position of the list
    * @return the value at index location
    */
    @Override
    public int get(int index) {
        return imList.get(index);
    }
    
    /**
    * Returns the concatenation of the current list and other list.
    * @param list The other list
    * @return An immutable list containing elements from both list.
    */
    @Override
    public IImmutableList concat(IImmutableList list) {
        
        //get size of concatentation
        int concatSize = list.size() + this.imList.size();
        //create int array 
        int[] tempList = new int[concatSize];
        //loop through parameter list and add to tempList
        for (int i = 0; i < list.size(); i++) {
            tempList[i] = list.get(i);
        }
        //loop through called object and add to tempList
        for (int i = 0; i < this.imList.size(); i++) {
            //adjust insertion point by size of array already added
            tempList[i + (list.size())] = imList.get(i);
        }
        //return new ImmutableList
        return new ImmutableList(tempList);
    }
    
    /**
    * Returns the number of elements in the list.
    * @return number of elements in list.
    */
    @Override
    public int size() {
        return this.imList.size();
    }
    
    /**
    * Return a string presentation of the list.
    * The content is enclosed in [ ], Each element is separated by a comma.
    * @return string representation of the list.
    */
    @Override
    public String toString() {
        //start string
        String tempString = "[";
        //get first element
        tempString += imList.get(0).toString();
        //loop through remaining elements and add to string with comma
        for (int i = 1; i <this.imList.size(); i++) {
            tempString += ", " + imList.get(i).toString();
        }
        //finish string
        tempString += "]";
        //return string
        return tempString;
    }
}
