package HW5;

/**
* This <code>BashTerminal</code> class The class should contain a single 
* main method which allows a user to interact with a file system implemented
* by an instance of DirectoryTree.
* 
* All extra credit implemented.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/
import java.util.*;

public class BashTerminal
{
    public static void main(String[] args) 
    {
        DirectoryTree tree = new DirectoryTree();
        Scanner stdin = new Scanner(System.in);
        String command = "";
        System.out.println("Starting bash terminal.");
        while(!command.equals("exit"))
        {
            try
            {
                System.out.print("[mishi@111548035]: $");
                command = stdin.nextLine();
                String commandType = command;
                if (commandType.contains(" ")) 
                {
                    commandType = commandType.substring(0,
                      commandType.indexOf(" "));
                    command = command.substring(command.indexOf(" ")+1);
                }
                switch(commandType)
                {
                    //Print the "present working directory" of the cursor node.
                    case("pwd"):
                        System.out.println(tree.presentWorkingDirectory());
                    break;
                    case("ls"):
                        /**
                         * Recursive traversal of the directory tree. 
                         * Prints the entire tree starting from the cursor in 
                         * pre-order traversal
                         */
                        if (command.equals("-R")) 
                        {
                            tree.printDirectoryTree();
                        }
                        // List the names of all the child directories or
                        // files of the cursor.
                        else if(command.equals(commandType))
                        {
                            System.out.println(tree.listDirectory());
                        }
                        else
                        {
                            System.out.println("ERROR: Unrecognized command.");
                            System.out.println("Please check if there is a "
                              + "misspelling\nor a white space typed at the end"
                              + " of the input command.");
                        }
                    break;
                    // Command begin with "cd"
                    case("cd"):
                        // reset the cursor to the root.
                        if (command.equals("/")) 
                        {
                            tree.resetCursor();
                        }
                        // set the cursor to the parent.
                        else if (command.equals("..")) 
                        {
                            String path = tree.getCursor().getPath();
                            if (!path.equals("root")) 
                            {
                                path = path.substring(0,path.lastIndexOf("/"));
                                tree.changeDirectory(path);
                            }
                            else
                            {
                                System.out.println("ERROR: Already at root"
                                  +" directory.");
                                System.out.println("Please check if there is a "
                                  + "misspelling\nor a white space typed at "
                                  + "the end of the input command.");
                            }
                        }
                        // set the cursor to a directory with a path.
                        else
                        {
                            if (command.contains("/")) 
                            {
                            	if (!command.substring(0,command.indexOf("/"))
                            	  .equals("root"))
                            	{
                                    String node = command.substring
                                      (command.lastIndexOf("/")+1);
                                    String path = "";
                                    DirectoryNode target 
                                      = tree.search(node,tree.getRoot());
                                    if(target == null)
                                    	throw new NodeNotFoundException();
                                    else
                                    	path = target.getPath();
                                    tree.changeDirectory(path);
                                }
                                else
                                    tree.changeDirectory(command);
                            }
                            else if (!command.equals("")
                              &&!command.contains(" ")&&!command.equals("cd"))
                                tree.changeDirectory(command);
                            else
                            {
                                System.out.println
                                  ("ERROR: Unrecognized command.");
                                System.out.println("Please check if there is a "
                                  + "misspelling\nor a white space typed at "
                                  + "the end of the input command.");
                            }
                        }
                    break;
                    /**
                     * Creates a new directory with the indicated name as a 
                     * child of the cursor, as long as there is room.
                     */
                    case("mkdir"):
                        if(!command.equals("")&&!command.equals("mkdir")
                          &&!command.contains(" "))
                            tree.makeDirectory(command);
                        else
                        {
                            System.out.println
                              ("ERROR: Unrecognized command.");
                            System.out.println("Please check if there is a "
                              + "misspelling\nor a white space typed at the end"
                              + " of the input command.");
                        }
                    break;
                    /**
                     * Creates a new file with the indicated name as a child 
                     * of the cursor, as long as there is room.
                     */
                    case("touch"):
                        if(!command.equals("")&&!command.equals("touch")
                          &&!command.contains(" "))
                            tree.makeFile(command);
                        else
                        {
                            System.out.println
                              ("ERROR: Unrecognized command.");
                            System.out.println("Please check if there is a "
                              + "misspelling\nor a white space typed at the end"
                              + " of the input command.");
                        }
                    break;
                    /**
                     * Finds the node in the tree with the indicated name 
                     * and prints the path.
                     */
                    case("find"):  
                        tree.searchPrint(command,tree.getRoot());
                        DirectoryNode target 
                          = tree.search(command,tree.getRoot());
                        if(target == null)
                        {
                            System.out.println("ERROR: No such file exits.");
                            System.out.println("Please check if there is a "
                              + "misspelling\nor a white space typed at the end"
                              + " of the input command.");
                        }
                    break;
                    /**
                     * Moves a file or directory specified by src to dst,
                     * including all children.
                     */
                    case("mv"):
                        if (command.contains(" ")&&command.contains("/")
                          &&command.indexOf(" ")!=command.length()-1
                          &&command.contains("root"))
                        {
                            String src = command.substring
                              (0,command.indexOf(" "));
                            String dst = command.substring
                              (command.indexOf(" ")+1);
                            String srcParent = src.substring
                              (0,src.lastIndexOf("/"));
                            String srcName = src.substring
                              (src.lastIndexOf("/")+1);
                            tree.changeDirectory(srcParent);
                            DirectoryNode temp = new DirectoryNode();
                            for(int i = 0;i<10;i++)
                            {
                                if(tree.getCursor().getChild(i)!= null
                                  &&tree.getCursor().getChild(i).getName()
                                  .equals(srcName))
                                    {
                                        temp = tree.getCursor().getChild(i);
                                        tree.changeDirectory(dst);
                                        tree.getCursor().addChild(temp);
                                        tree.changeDirectory(srcParent);
                                        tree.getCursor().setChild(null,i);
                                    }
                            }
                            tree.resetCursor();
                        }
                        else if(command.contains(" ")&&command.contains("/")
                          &&command.indexOf(" ")!=command.length()-1)
                        {
                            throw new NodeNotFoundException();
                        }
                        else
                        {
                            System.out.println("ERROR: Unrecognized command.");
                            System.out.println("Please check if there is a "
                              + "misspelling \nor a white space"
                              + " typed at the end of the input command.");
                        }
                            
                    break;
                    default:
                        if(!command.equals("exit"))
                        {
                            System.out.println("ERROR: Unrecognized command.");
                            System.out.println("Please check if there is a "
                              + "misspelling\nor a white space typed at the end"
                              + " of the input command.");
                        }
                }
            }
            catch(NotADirectoryException nde)
            {
                System.out.println("ERROR: Cannot change directory "
                  + "into file.");
                System.out.println("Please check if there is a misspelling\nor "
                  + "a white space typed at the end of the input command.");
            }
            catch(FullDirectoryException fde)
            {
                System.out.println("ERROR: Present directory is full.");
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println("ERROR: Input name is invalid.");
                System.out.println("Please check if there is a misspelling\nor "
                  + "a white space typed at the end of the input command.");
            }
            catch (NodeNotFoundException nnfe) {
                System.out.println("ERROR: No such directory named '"
                  +command+"'.");
                System.out.println("Please check if there is a misspelling\nor "
                  + "a white space typed at the end of the input command.");
            }
            catch (NameAlreadyExistException nae)
            {
                System.out.println("ERROR: Can't create. File exists.");
                System.out.println("Please check if there is a misspelling\nor "
                  + "a white space typed at the end of the input command.");
            }
        }
        System.out.println("Program terminating normally");
    }
}
