package com.skip;

import java.io.Serializable;
import javax.persistence.Transient;

/**
 * Base class for model objects
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.1 $, $Date: 2006/02/15 10:42:37 $
 */
public class SkipModelBase implements Serializable {

   private boolean selected;

   /**
    * Convenience method to check if the domain object is selected.
    * @return true if object was selected.
    */
   @Transient
   public boolean isSelected() {
      return selected;
   }
   /**
    * Convenience method to mark the domain object as selected.
    * @param selected
    */
   @Transient
   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
