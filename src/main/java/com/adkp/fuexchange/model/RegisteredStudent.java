package com.adkp.fuexchange.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "RegisteredStudent")
public class RegisteredStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registeredStudentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student studentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Role roleId;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Seller sellerId;

    @OneToMany(mappedBy = "chatMessageId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<ChatMessage> chatMessageId;

    @OneToMany(mappedBy = "orderId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Order> orderId;
    public RegisteredStudent(Student studentId, Role roleId, String password) {
        this.studentId = studentId;
        this.roleId = roleId;
        this.password = password;
    }
}
