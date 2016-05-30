package app.item;
// Generated Feb 24, 2016 10:44:15 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ItemSupplierId generated by hbm2java
 */
@Embeddable
public class ItemSupplierId  implements java.io.Serializable {


     private long idSupplier;
     private long idItem;

    public ItemSupplierId() {
    }

    public ItemSupplierId(long idSupplier, long idItem) {
       this.idSupplier = idSupplier;
       this.idItem = idItem;
    }
   


    @Column(name="id_supplier", nullable=false)
    public long getIdSupplier() {
        return this.idSupplier;
    }
    
    public void setIdSupplier(long idSupplier) {
        this.idSupplier = idSupplier;
    }


    @Column(name="id_item", nullable=false)
    public long getIdItem() {
        return this.idItem;
    }
    
    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ItemSupplierId) ) return false;
		 ItemSupplierId castOther = ( ItemSupplierId ) other; 
         
		 return (this.getIdSupplier()==castOther.getIdSupplier())
 && (this.getIdItem()==castOther.getIdItem());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getIdSupplier();
         result = 37 * result + (int) this.getIdItem();
         return result;
   }   


}

