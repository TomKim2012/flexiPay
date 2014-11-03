package com.workpoint.mwallet.server.dao.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;

import com.workpoint.mwallet.server.util.CryptoUtils;

@Entity(name="BUser")
@Table(uniqueConstraints={@UniqueConstraint(columnNames="userId")})
@NamedQuery(name="User.getUserByUserId", query="from BUser u where u.userId=:userId")
public class User extends PO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String userId;
	
	private String password;
	
	@Column(nullable=false)
	private String lastName;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(length=100)
	private String email;
	
	@OneToMany(mappedBy="owner")
	private Set<TillModel> tillsOwned = new HashSet<TillModel>();
	
	@OneToMany(mappedBy="salesPerson")
	private Set<TillModel> salesPersonTills = new HashSet<TillModel>();
	
	@OneToMany(mappedBy="boss")
	private Set<User> cashiers = new HashSet<User>();
	
	@ManyToOne
	@JoinColumn(name="bossId", referencedColumnName="userId")
	private User boss;
	
	private boolean isArchived;
	
	private String linkCode;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name="UserGroup",
		joinColumns={@JoinColumn(name="userid")},
		inverseJoinColumns={@JoinColumn(name="groupid")}
	)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.MERGE})
	private Collection<Group> groups = new HashSet<>();
	
	public User(){
		this.isArchived=false;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void addGroup(Group group){
		groups.add(group);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean checkPassword(String plainPassword){
		
		assert plainPassword!=null && password!=null;
		if(password==null){
			throw new NullPointerException("DB password should not be null");
		}
		
		return CryptoUtils.getInstance().checkPassword(plainPassword, password);
	}

	public void setPassword(String plainPassword) {
		this.password = CryptoUtils.getInstance().encryptPassword(plainPassword);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public void setGroups(Collection<Group> groups) {
		this.groups = groups;
	}

	public User getBoss() {
		return boss;
	}

	public void setBoss(User boss) {
		this.boss = boss;
	}

	public Set<User> getCashiers() {
		return cashiers;
	}

	public String getLinkCode() {
		return linkCode;
	}

	public void setLinkCode(String linkCode) {
		this.linkCode = linkCode;
	}
}
