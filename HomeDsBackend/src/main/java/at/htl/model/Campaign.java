package at.htl.model;

import at.htl.enums.XiboEnum;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Campaign.GetAll",
                query = "select d from Campaign d"),
        @NamedQuery(name = "Campaign.GetAllDataSet",
                query = "select d from Campaign d where d.xiboEnum = 1"),
        @NamedQuery(name = "Campaign.GetAllMedia",
                query = "select d from Campaign d where d.xiboEnum = 0")
})

@Table
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long campaignId;

    private XiboEnum xiboEnum;

    public Campaign(long campaignId) {
        this.campaignId = campaignId;
    }

    public Campaign() {
    }

    public Campaign(long campaignId, XiboEnum xiboEnum) {
        this.campaignId = campaignId;
        this.xiboEnum = xiboEnum;
    }

//region Getter & Setter

    public XiboEnum getXiboEnum() {
        return xiboEnum;
    }

    public void setXiboEnum(XiboEnum xiboEnum) {
        this.xiboEnum = xiboEnum;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //endregion
}
