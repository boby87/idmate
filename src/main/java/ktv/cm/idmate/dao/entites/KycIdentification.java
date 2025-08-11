package ktv.cm.idmate.dao.entites;

import jakarta.persistence.*;

@Entity
public class KycIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "text")
    private String face;
    @Column(columnDefinition = "text")
    private String signature;
    private String ping;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Column(columnDefinition = "text")
    private String document;
    private boolean isVerifiedDocument;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private boolean isVerified;

    public boolean isVerifiedDocument() {
        return isVerifiedDocument;
    }

    public void setVerifiedDocument(boolean verifiedDocument) {
        isVerifiedDocument = verifiedDocument;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
