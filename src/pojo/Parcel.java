package pojo;

import java.math.BigDecimal;

public class Parcel<K extends Number> {

    //Parcel Type
    private String type;

    //Parcel Id
    private Integer parcelId;

    //Parcel Weight
    private K weight;

    // item quantity
    private Integer itemQuantity;

    /**
     * Constructor to create a parcel
     *
     * @param parcelId Unique identification for the parcel
     * @param weight   Weight of the parcel
     */
    public Parcel(Integer parcelId, K itemWeight, String type, Integer itemQuantity) {
        this.parcelId = parcelId;
        this.type = type;
        this.itemQuantity = itemQuantity;
        this.weight = convertToK(itemWeight, itemQuantity);
    }

    private K convertToK(K itemWeight, Integer itemQuantity) {
        if (itemWeight instanceof Integer) {
            Integer parcelTotalWeight = (Integer) itemWeight * itemQuantity;
            return (K) parcelTotalWeight;
        } else if (itemWeight instanceof Double) {
            BigDecimal parcelTotalWeight = BigDecimal.valueOf(itemQuantity).multiply(BigDecimal.valueOf((Double) itemWeight));
            return (K) (Double) parcelTotalWeight.doubleValue();
        }
        throw new IllegalArgumentException("The parcel weight must be a number !");
    }

    //Getter and Setter

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer truckId) {
        this.parcelId = truckId;
    }

    public K getWeight() {
        return weight;
    }

    public void setWeight(K weight) {
        this.weight = weight;
    }

    /**
     * Build a string that contains the parcel information
     *
     * @return Formated string that contains the parcel's information
     */
    @Override
    public String toString() {
        return "Parcel{" +
                "type='" + type + '\'' +
                ", parcelId=" + parcelId +
                ", weight=" + weight +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
