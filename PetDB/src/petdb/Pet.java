/*
 * CSC 422
 * Adam Maser
 * 10/27/2019
 * assignment 1: version control practice with pet program
 */
package petdb;

/**
 *
 * @author adammaser
 */
public class Pet {
    private String name;
    private int age;
    
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
