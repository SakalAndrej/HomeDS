package at.htl.model;

import javax.persistence.*;

@NamedQueries(
        {
                @NamedQuery(name = "Campaign.GetAll",
                        query = "select d from Campaign d")
        })

@Table
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long campaignId;

    public Campaign(long campaignId) {
        this.campaignId = campaignId;
    }

    public Campaign() { }

    //region Getter & Setter

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
