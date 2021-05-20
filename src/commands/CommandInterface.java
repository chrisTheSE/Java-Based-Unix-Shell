package commands;

import driver.JShell;


/**
 * CommandInterface is the interface which defines the structure of all
 * commands, their constants and how to access them, and the method run to run
 * them.
 */
public interface CommandInterface<T> {
  /**
   * Get the output for the command.
   * 
   * @return returns the output for the command
   */
  public String getOutput();

  /**
   * Set the output.
   * 
   * @param output, the output to set for the command
   */
  public void setOutput(String output);

  /**
   * Get the errors for the command.
   * 
   * @return returns the error for the command
   */
  public String getErrors();

  /**
   * Set the error.
   * 
   * @param errors, the errors to set for the command
   */
  public void setErrors(String errors);

  /**
   * Set the identifier.
   * 
   * @param iden, the identifier to set for the command
   */
  public void setIdentifier(String iden);

  /**
   * Set the max number of arguments.
   * 
   * @param num, the number to set for the max number of arguments of the
   *        command
   */
  public void setMaxNumOfArguments(int num);

  /**
   * Set the min number of arguments.
   * 
   * @param num, the number to set for the min number of arguments of the
   *        command
   */
  public void setMinNumOfArguments(int num);

  /**
   * Set the error message for when too many arguments are given to the command.
   * 
   * @param err, the error message to set for too many arguments
   */
  public void setErrorTooManyArguments(String err);

  /**
   * Set the error message for missing operand(s) for the command.
   * 
   * @param err, the error message to set for missing operands
   */
  public void setMissingOperand(String err);

  /**
   * Set the description for the command.
   * 
   * @param description, the description for the command
   */
  public void setDescription(String description);

  /**
   * Get the identifier of the command.
   * 
   * @return returns a string of the command's identifier
   */
  public String getIdentifier();

  /**
   * Get the max number of arguments for the command.
   * 
   * @return returns an int of the max number of arguments for the command
   */
  public int getMaxNumOfArguments();

  /**
   * Get the min number of arguments for the command.
   * 
   * @return returns an int of the min number of arguments for the command
   */
  public int getMinNumOfArguments();

  /**
   * Get the error message for too many arguments for the command.
   * 
   * @return return a string of the error message for too many arguments
   */
  public String getErrorTooManyArguments();

  /**
   * Get the error message for missing operand(s) for the command.
   * 
   * @return return a string of the error message for missing operand(s)
   */
  public String getErrorMissingOperand();

  /**
   * Get the description for the command.
   * 
   * @return returns a string of the description for the command
   */
  public String getDescription();

  /**
   * Checks if tokens has a valid redirection
   * 
   * @param tokens, an array of string tokens containing command arguments
   * @param shell an instance of JShell
   * @return returns true if tokens has a valid redirection, else false
   */
  public Command checkRun(String[] tokens, JShell shell);


  /**
   * Runs the command (to be implemented for each command, overridden by each
   * subclass).
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell an instance of JShell
   * @return returns an instance of command with output and errors
   */
  public Command run(String[] tokens, JShell shell);
}
