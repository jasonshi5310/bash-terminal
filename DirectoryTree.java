package HW5;

/**
* This <code>DirectoryTree</code> class implements a ternary
* (3-child) tree of DirectoryNodes. 
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/

public class DirectoryTree
{
    private DirectoryNode root; //The root of the tree.
    private DirectoryNode cursor; //The cursor the present working directory.

    /**
    * Returns the root of the tree.
    *
    * @return
    *    The root of the tree.
    **/
    public DirectoryNode getRoot()
    {
        return root;
    }

    /**
    * Returns the cursor of the tree.
    *
    * @return 
    *    The cursor of the tree.
    **/
    public DirectoryNode getCursor()
    {
        return cursor;
    }

    /**
    * Sets a new root for this tree.
    *
    * @param newRoot
    *    The new root for this tree.
    **/
    public void setRoot(DirectoryNode newRoot)
    {
        this.root = newRoot;
    }

    /**
    * Sets a new cursor for this tree.
    *
    * @param newCursor
    *    The new cursor for this tree.  
    **/
    public void setCursor(DirectoryNode newCursor)
    {
        this.cursor = newCursor;
    }

    /**
    * Initializes a DirectoryTree object with a single DirectoryNode 
    * named "root".
    *
    * <dt>Postconditions:
    *     <dd>The tree contains a single DirectoryNode named "root", and both 
    *       cursor and root reference this node.
    *       NOTE: Do not confuse the name of the directory with the name 
    *       of the reference variable. 
    *       The DirectoryNode member variable of DirectoryTree named root 
    *       should reference a DirectoryNode who's name is "root",
    *       i.e. root.getName().equals("root") is true.
    **/
    public DirectoryTree()
    {
        this.root = new DirectoryNode();
        this.root.setName("root");
        this.root.setIsFile(false);
        this.root.setPath("root");
        this.cursor = this.root;
    }

    /**
    * Moves the cursor to the root node of the tree.
    *
    * <dt>Postconditions:
    *    <dd>The cursor now references the root node of the tree.
    **/
    public void resetCursor()
    {
        this.cursor = this.root;
    }

    /**
    * Moves the cursor to the directory with the name indicated by name.
    *
    * @param name
    *    The name of the directory which cursor will be moved to.
    *
    * <dt>Preconditions:
    *    <dd>'name' references a valid directory ('name' 
    *      cannot reference a file).
    *
    * <dt>Postconditions:
    *    <dd>The cursor now references the directory with the name indicated
    *      by name. If a child could not be found with that name, then the user
    *      is prompted to enter a different directory name. If the name was not
    *      a directory, a NotADirectoryException hs been thrown.
    *
    * @exception NotADirectoryException
    *    Thrown if the node with the indicated name is a file, as files cannot
    *    be selected by the cursor, or cannot be found.
    *    NOTE: In modern operating systems, the change directory command 
    *    (cd {path}) allows the user to jump from a current directory to any
    *    other directory in the file system given an absolute or relative path.
    *    In this assignment, you will only be required to change directory to 
    *    direct children of the cursor (cd {dir}). However, you may implement 
    *    the more general command for absolute paths for extra credit.
    * 
    * @exception NodeNotFoundException
    *    Thrown if the node is not found.
    **/
    public void changeDirectory(String name) throws NotADirectoryException,
      NodeNotFoundException
    {
        
        // If name is direct child or root (condition without a "/")
        if (!name.contains("/")) 
        {
            if (name.equals("root")) 
            {
                cursor = root;
            }
            else
            {
                DirectoryNode temp = new DirectoryNode();
                for (int i = 0;i<10 ;i++ ) 
                {
                    if(cursor.getChild(i)!= null 
                      && cursor.getChild(i).getName().equals(name))
                        temp = cursor.getChild(i);
                }
                if(temp.getPath() == null)
                    throw new NodeNotFoundException();
                if(temp.getIsFile())
                    throw new NotADirectoryException();
                else
                    cursor = temp;
            }
        }
        else
        {
            DirectoryNode ptr = root;
            int postion = name.indexOf("/")+1;
            name = name.substring(postion);
            String currentNode = "";
            while(!name.equals(""))
            {
                boolean ifNotFound = true;
                if (!name.contains("/")) 
                {
                    currentNode = name;
                }
                else
                    currentNode = name.substring(0,name.indexOf("/"));
                for(int i = 0;i<10;i++)
                {
                    if (ptr.getChild(i)!=null
                      &&ptr.getChild(i).getName().equals(currentNode)) 
                    {
                        ptr = ptr.getChild(i);
                        if (name.contains("/")) 
                        {
                            postion = name.indexOf("/")+1;
                            name = name.substring(postion);
                        }
                        else
                            name = "";
                        ifNotFound = false;
                        break;
                    }
                }
                if (ifNotFound) 
                    throw new NodeNotFoundException();
            }
            if (ptr.getIsFile()) 
            {
                throw new NotADirectoryException();
            }
            else
                cursor = ptr;
        }
    }

    /**
    * Returns a String containing the path of directory names from the root 
    * node of the tree to the cursor, with each name separated by a forward
    * slash "/". e.g. root/home/user/Documents if the cursor is at 
    * Documents in the example above.
    *
    * <dt>Postconditions:
    *    <dd>The cursor remains at the same DirectoryNode.
    *
    * @return
    *    A String containing the path of directory names from the root 
    *    node of the tree to the cursor.
    **/
    public String presentWorkingDirectory()
    {
        return cursor.getPath();
    }

    /**
    * Returns a String containing a space-separated list of names of all
    * the child directories or files of the cursor.
    * e.g. dev home bin if the cursor is at root in the example above.
    *
    * <dt>Postconditions:
    *    <dd>The cursor remains at the same DirectoryNode.
    *
    * @return
    *    A formatted String of DirectoryNode names.
    **/
    public String listDirectory()
    {
        String str = "";
        for (int i = 0;i<10;i++)
        {
            if(cursor.getChild(i)!=null)
            str = str + cursor.getChild(i).getName()+" ";
        }
        return str;
    }

    /**
    * Prints a formatted nested list of names of all the nodes in the 
    * directory tree, starting from the cursor.
    *  See sample I/O for an example.
    *
    * <dt>Postconditions:
    *    <dd>The cursor remains at the same DirectoryNode.
    **/
    public void printDirectoryTree()
    {
        printPreOrder("", cursor);
    }

    /**
    * Recursively print a list of name from a giving node.
    *
    * @param node
    *    The node needs to be printed.
    * 
    * @param indent
    *    The indent for each node.
    **/
    public static void printPreOrder(String indent, DirectoryNode node)
    {
        if(node.getIsFile())
            System.out.println(indent+"- "+node.getName());
        else
        {
            System.out.println(indent+"|- "+node.getName());
            indent = indent + "    ";
            for (int i = 0; i<10;i++) 
            {
                if(node.getChild(i)!=null)
                {
                    printPreOrder(indent, node.getChild(i));
                }
            }
        }
    }

    /**
    * Creates a directory with the indicated name and adds it to the children
    * of the cursor node. Remember that children of a node are added in 
    * left-to-right order.
    *
    * @param name
    *    The name of the directory to add.
    *
    * <dt>Preconditions:
    *    <dt>'name' is a legal argument (does not contain spaces " " or
    *    forward slashes "/").
    *
    * <dt>Postconditions:
    *    <dd>A new DirectoryNode has been added to the children of the cursor,
    *    or an exception has been thrown.
    *
    * @exception IllegalArgumentException
    *    Thrown if the 'name' argument is invalid.
    *
    * @exception FullDirectoryException
    *    Thrown if all child references of this directory are occupied.
    * 
    * @exception NotADirectoryException
    *    Thrown if the current node is a file, as files cannot contain 
    *    DirectoryNode references (i.e. all files are leaves).
    * 
    * @exception NameAlreadyExistException
    *    Thrown if a file with the same name already exist in the same level.
    **/
    public void makeDirectory(String name) 
      throws IllegalArgumentException, FullDirectoryException, 
      NotADirectoryException, NameAlreadyExistException
    {
        if (name.contains(" ") | name.contains("/"))
        {
            throw new IllegalArgumentException();
        }
        DirectoryNode temp = new DirectoryNode();
        temp.setName(name);
        temp.setIsFile(false);
        cursor.addChild(temp);
    }

    /**
    * Creates a file with the indicated name and adds it to the children of
    * the cursor node. Remember that children of a node are added in 
    * left-to-right order.
    *
    * @param name
    *    The name of the file to add.
    *
    * <dt>Preconditions:
    *    <dd>'name' is a legal argument (does not contain spaces " " 
    *    or forward slashes "/").
    *
    * <dt>Postconditions:
    *    <dd>A new DirectoryNode has been added to the children of the
    *    cursor, or an exception has been thrown.
    *
    * @exception IllegalArgumentException
    *    Thrown if the 'name' argument is invalid.
    *
    * @exception FullDirectoryException
    *    Thrown if all child references of this directory are occupied.
    * 
    * @exception NotADirectoryException
    *    Thrown if the current node is a file, as files cannot contain 
    *    DirectoryNode references (i.e. all files are leaves).
    * 
    * @exception NameAlreadyExistException
    *    Thrown if a file with the same name already exist in the same level.
    **/
    public void makeFile(String name) 
      throws IllegalArgumentException, FullDirectoryException,
      NotADirectoryException, NameAlreadyExistException
    {
        if (name.contains(" ") | name.contains("/"))
        {
            throw new IllegalArgumentException();
        }
        DirectoryNode temp = new DirectoryNode();
        temp.setName(name);
        temp.setIsFile(true);
        cursor.addChild(temp);
    }

    /**
    * Recursively search a tree and find the node with the given name.
    *
    * @param name
    *    The name of the node needed to be found.
    * 
    * @param node
    *    The node being searched currently.
    *
    * @return
    *    if found, the node of DirectoryTree object; otherwise, null;
    **/
    public DirectoryNode search(String name, DirectoryNode node)
    {
        if(node!=null)
        {
            if (node.getName().equals(name)) 
            {
                return node;
            }
            else
            {
                for (int i = 0;i<10 ;i++ ) 
                {
                    DirectoryNode nextNode = search(name, node.getChild(i));
                    if (nextNode!= null) 
                    {
                     	return nextNode;
                    } 
                }
            }
        }
        return null;
    }
    
        /**
    * Recursively search a tree, find all the node with the given
    * name and print.
    *
    * @param name
    *    The name of the node needed to be found.
    * 
    * @param node
    *    The node being searched currently.
    *
    * @return
    *    if found, the node of DirectoryTree object; otherwise, null;
    **/
    public DirectoryNode searchPrint(String name, DirectoryNode node)
    {
        if(node!=null)
        {
            if (node.getName().equals(name)) 
            {
                System.out.println(node.getPath());
                return node;
            }
            else
            {
                DirectoryNode nextNode = null;
                for (int i = 0;i<10 ;i++ ) 
                {
                    nextNode = searchPrint
                      (name, node.getChild(i));
//                    if (nextNode!= null) 
//                    {
//                     	return nextNode;
//                    } 
                }
                return nextNode;
            }
        }
        return null;
    }
}

class NodeNotFoundException extends Exception
{
    public NodeNotFoundException()
    {

    }
}
