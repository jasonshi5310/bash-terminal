package HW5;

/**
* This <code>DirectoryNode</code> class represents a node in the file tree. 
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/

public class DirectoryNode
{
    private DirectoryNode left; //The left child of this node.
    private DirectoryNode middle; //The middle child of this node.
    private DirectoryNode right; //The right child of this node.
    private String name; //The name of the DirectoryNode.
    //If true, the DirectoryNode is a file; otherwise, a folder.
    private boolean isFile; 
    private String path; //The path of the node from the node.
    // The list storing up to 10 child of the current node.
    private DirectoryNode[] childList = new DirectoryNode[10]; 
    
    /**
    * Returns a instance of the DirectoryNode.
    **/
    public DirectoryNode()
    {
        childList[0] = left;
        childList[1] = middle;
        childList[2] = right;
    }

    /**
    * Returns the child node with the given position of 
    * the child list.
    *
    * @param position
    *    The position of the child in the childList.
    *
    * @return
    *    The child node of the child list.
    **/
    public DirectoryNode getChild(int position)
    {
        return childList[position];
    }

    /**
    * Set a new child for this Directorynode object.
    *
    * @param newChild
    *    The new name for this DirectoryNode object.
    *
    * @param position
    *    The position of the new child.
    **/
    public void setChild(DirectoryNode newChild, int position)
    {
        this.childList[position] = newChild;
    }
 
    /**
    * Returns the name of this DirectoryNode object.
    *
    * @return
    *    The name of this DirectoryNode object.
    **/
    public String getName()
    {
        return name;
    }

    /**
    * Set a new name for this Directorynode object.
    *
    * @param newName
    *    The new name for this DirectoryNode object.
    **/
    public void setName(String newName)
    {
        this.name = newName;
    }

    /**
    * Returns the path of this DirectoryNode object.
    *
    * @return
    *    The path of this DirectoryNode object.
    **/
    public String getPath()
    {
        return path;
    }

    /**
    * Set a new path for this Directorynode object.
    *
    * @param newPath
    *    The new path for this DirectoryNode object.
    **/
    public void setPath(String newPath)
    {
        this.path = newPath;
    }

    /**
    * Returns ture if this DirectoryNode object is a file;
    * if false, a folder.
    *
    * @return
    *    if ture, this node is a file; otherwise, a folder.
    **/
    public boolean getIsFile()
    {
        return isFile;
    }

    /**
    * Set a new value to determine if this Directorynode
    * object is a file or folder.
    *
    * @param newIsFile
    *    The new value for isFile of this DirectoryNode object.
    **/
    public void setIsFile(boolean newIsFile)
    {
        this.isFile = newIsFile;
    }

    /**
    * Returns the left child of this DirectoryNode object.
    *
    * @return
    *    The left child of this DirectoryNode object.
    **/
    public DirectoryNode getLeft()
    {
        return left;
    }

    /**
    * Returns the middle child of this DirectoryNode object.
    *
    * @return
    *    The middle child of this DirectoryNode object.
    **/
    public DirectoryNode getMiddle()
    {
        return middle;
    }

    /**
    * Returns the right child of this DirectoryNode object.
    *
    * @return
    *    The right child of this DirectoryNode object.
    **/
    public DirectoryNode getRight()
    {
        return right;
    }

    /**
    * Adds newChild to any of the open child positions of this node
    * (left, middle, or right).
    * NOTE: Children should be added to this node in left-to-right order,
    * i.e. left is filled first, middle is filled second, and right 
    * is filled last.
    *
    * @param newChild
    *    The new directoryNode added as a new child of this node.
    *
    * <dt>Preconditions:
    *    <dd>This node is not a file.
    *    <dd>There is at least one empty position in the children of 
    *        this node (left, middle, or right).
    *
    * <dt>Postconditions:
    *    <dd>newChild has been added as a child of this node. If there
    *        is no room for a new node, throw a FullDirectoryException.
    *
    * @exception NotADirectoryException
    *    Thrown if the current node is a file, as files cannot contain 
    *    DirectoryNode references (i.e. all files are leaves).
    *
    * @exception FullDirectoryException
    *    Thrown if all child references of this directory are occupied.
    * 
    * @exception NameAlreadyExistException
    *    Thrown if a file with the same name already exist in the same level.
    **/
    public void addChild(DirectoryNode newChild) 
      throws FullDirectoryException, NotADirectoryException,
      NameAlreadyExistException
    {
        if (this.isFile)
            throw new NotADirectoryException();
        boolean isFull = true;
        int position = 0;
        while(isFull)
        {
            if (position >= 10)
                break;
            if(childList[position]!= null 
              && childList[position].getName().equals(newChild.getName()))
                throw new NameAlreadyExistException();
            if (childList[position++] == null)
                isFull = false;
        }
        if(isFull)
        	throw new FullDirectoryException();
        newChild.setPath(this.path + "/" +newChild.getName());
        childList[--position] = newChild;
    }
}

class NotADirectoryException extends Exception
{
    public NotADirectoryException()
    {

    }
}

class FullDirectoryException extends Exception
{
    public FullDirectoryException()
    {

    }
}

class NameAlreadyExistException extends Exception
{
    public NameAlreadyExistException()
    {
        
    }
}