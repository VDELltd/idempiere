/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.idempiere.cache.ImmutablePOSupport;

/**
 *	Business Partner Model
 *
 *  @author Jorg Janke
 *  @version $Id: MBPartner.java,v 1.5 2006/09/23 19:38:07 comdivision Exp $
 * 
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1568774 ] Walk-In BP: invalid created/updated values
 * 			<li>BF [ 1817752 ] MBPartner.getLocations should return only active one
 *  @author Armen Rizal, GOODWILL CONSULT  
 *      <LI>BF [ 2041226 ] BP Open Balance should count only Completed Invoice
 *			<LI>BF [ 2498949 ] BP Get Not Invoiced Shipment Value return null
 */
public class MBPartner extends X_C_BPartner implements ImmutablePOSupport
{
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 2256035503713773448L;

	/**
	 * 	Create new Business Partner from template (not save)
	 * 	@param ctx context
	 * 	@param AD_Client_ID client
	 * 	@return new Business Partner created from template or null
	 */
	public static MBPartner getTemplate (Properties ctx, int AD_Client_ID)
	{
		MBPartner template = getBPartnerCashTrx (ctx, AD_Client_ID);
		if (template == null)
			template = new MBPartner (ctx, 0, null);
		//	Reset
		if (template != null)
		{
			template.set_ValueNoCheck ("C_BPartner_ID", Integer.valueOf(0));
			template.set_ValueNoCheck ("C_BPartner_UU", (String)null);
			template.setAD_OrgBP_ID(0);
			template.setLogo_ID(0);
			template.setValue ("");
			template.setName ("");
			template.setName2 (null);
			template.setDUNS("");
			template.setFirstSale(null);
			//
			template.setSO_CreditLimit (Env.ZERO);
			template.setSO_CreditUsed (Env.ZERO);
			template.setTotalOpenBalance (Env.ZERO);
			//
			template.setActualLifeTimeValue(Env.ZERO);
			template.setPotentialLifeTimeValue(Env.ZERO);
			template.setAcqusitionCost(Env.ZERO);
			template.setShareOfCustomer(0);
			template.setSalesVolume(0);
			// Reset Created, Updated to current system time ( teo_sarca )
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			template.set_ValueNoCheck("Created", ts);
			template.set_ValueNoCheck("Updated", ts);
			template.set_ValueNoCheck("CreatedBy", Env.getAD_User_ID(ctx));
			template.set_ValueNoCheck("UpdatedBy", Env.getAD_User_ID(ctx));
		}
		return template;
	}	//	getTemplate

	/**
	 * 	Get Cash Trx Business Partner (as template for new BP)
	 * 	@param ctx context
	 * 	@param AD_Client_ID client
	 * 	@return Cash Trx Business Partner or null
	 */
	public static MBPartner getBPartnerCashTrx (Properties ctx, int AD_Client_ID)
	{
		MBPartner retValue = (MBPartner) MClientInfo.get(ctx, AD_Client_ID).getC_BPartnerCashTrx();
		if (retValue == null)
			s_log.log(Level.SEVERE, "Not found for AD_Client_ID=" + AD_Client_ID);
	
		return retValue;
	}	//	getBPartnerCashTrx

	/**
	 * 	Get BPartner with Value
	 *	@param ctx context 
	 *	@param Value value
	 *	@return BPartner or null
	 */
	public static MBPartner get (Properties ctx, String Value) {
		return get(ctx,Value,null);		
	}

	/**
	 * 	Get BPartner with Value in a transaction
	 *	@param ctx context 
	 *	@param Value value
	 * 	@param trxName transaction
	 *	@return BPartner or null
	 */
	public static MBPartner get (Properties ctx, String Value, String trxName)
	{
		if (Value == null || Value.length() == 0)
			return null;
		final String whereClause = "Value=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
		.setParameters(Value,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}	//	get
	
	/**
	 * 	Get BPartner with taxID in a transaction
	 *	@param ctx context 
	 *	@param taxID taxID
	 * 	@param trxName transaction
	 *	@return BPartner or null
	 */
	public static MBPartner getFirstWithTaxID (Properties ctx, String taxID, String trxName)
	{
		final String whereClause = "TaxID=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx, Table_Name, whereClause, trxName)
		.setParameters(taxID, Env.getAD_Client_ID(ctx))
		.setOrderBy(COLUMNNAME_C_BPartner_ID)
		.first();
		return retValue;
	}	//	getFirstWithTaxID

	/**
	 * 	Get BPartner with id
	 *	@param ctx context 
	 *  @param C_BPartner_ID
	 *	@return BPartner or null
	 */
	public static MBPartner get (Properties ctx, int C_BPartner_ID)
	{
		return get(ctx,C_BPartner_ID,null);
	}
	
	
	/**
	 * 	Get BPartner with id in a transaction
	 *	@param ctx context 
	 *  @param C_BPartner_ID
	 * 	@param trxName transaction
	 *	@return BPartner or null
	 */
	public static MBPartner get (Properties ctx, int C_BPartner_ID, String trxName)
	{
		final String whereClause = "C_BPartner_ID=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(ctx,I_C_BPartner.Table_Name,whereClause,trxName)
		.setParameters(C_BPartner_ID,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}	//	get

	/**
	 * 	Get Not Invoiced Shipment Value
	 *	@param C_BPartner_ID partner
	 *	@return value in accounting currency
	 */
	public static BigDecimal getNotInvoicedAmt (int C_BPartner_ID)
	{
		BigDecimal retValue = null;
		String sql = "SELECT COALESCE(SUM(COALESCE("
			+ "currencyBase((ol.QtyDelivered-ol.QtyInvoiced)*ol.PriceActual,o.C_Currency_ID,o.DateOrdered, o.AD_Client_ID,o.AD_Org_ID) ,0)),0) "
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE o.IsSOTrx='Y' AND Bill_BPartner_ID=?";			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, C_BPartner_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = rs.getBigDecimal(1);
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}	//	getNotInvoicedAmt
	
	/**	Static Logger				*/
	private static CLogger		s_log = CLogger.getCLogger (MBPartner.class);
	
	/**
	 * 	Constructor for new BPartner from Template
	 * 	@param ctx context
	 */
	public MBPartner (Properties ctx)
	{
		this (ctx, -1, null);
	}	//	MBPartner

	/**
	 * 	@param ctx context
	 * 	@param rs ResultSet to load from
	 * 	@param trxName transaction
	 */
	public MBPartner (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBPartner

    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param C_BPartner_UU  UUID key
     * @param trxName Transaction
     */
    public MBPartner(Properties ctx, String C_BPartner_UU, String trxName) {
        super(ctx, C_BPartner_UU, trxName);
		if (Util.isEmpty(C_BPartner_UU))
			setInitialDefaults();
    }

	/**
	 * 	@param ctx context
	 * 	@param C_BPartner_ID partner or 0 or -1 (load from template)
	 * 	@param trxName transaction
	 */
	public MBPartner (Properties ctx, int C_BPartner_ID, String trxName)
	{
		super (ctx, C_BPartner_ID, trxName);
		//
		if (C_BPartner_ID == -1)
		{
			initTemplate (Env.getContextAsInt(ctx, "AD_Client_ID"));
			C_BPartner_ID = 0;
		}
		if (C_BPartner_ID == 0)
			setInitialDefaults();
		if (log.isLoggable(Level.FINE)) log.fine(toString());
	}	//	MBPartner

	/**
	 * Set the initial defaults for a new record
	 */
	private void setInitialDefaults() {
		//
		setIsCustomer (true);
		setIsProspect (true);
		//
		setSendEMail (false);
		setIsOneTime (false);
		setIsVendor (false);
		setIsSummary (false);
		setIsEmployee (false);
		setIsSalesRep (false);
		setIsTaxExempt(false);
		setIsPOTaxExempt(false);
		setIsDiscountPrinted(false);
		//
		setSO_CreditLimit (Env.ZERO);
		setSO_CreditUsed (Env.ZERO);
		setTotalOpenBalance (Env.ZERO);
		setSOCreditStatus(SOCREDITSTATUS_NoCreditCheck);
		//
		setFirstSale(null);
		setActualLifeTimeValue(Env.ZERO);
		setPotentialLifeTimeValue(Env.ZERO);
		setAcqusitionCost(Env.ZERO);
		setShareOfCustomer(0);
		setSalesVolume(0);
	}

	/**
	 * 	Import Constructor
	 *	@param impBP import
	 */
	public MBPartner (X_I_BPartner impBP)
	{
		this (impBP.getCtx(), 0, impBP.get_TrxName());
		setClientOrg(impBP);
		setUpdatedBy(impBP.getUpdatedBy());
		//
		String value = impBP.getValue();
		if (value == null || value.length() == 0)
			value = impBP.getEMail();
		if (value == null || value.length() == 0)
			value = impBP.getContactName();
		setValue(value);
		String name = impBP.getName();
		if (name == null || name.length() == 0)
			name = impBP.getContactName();
		if (name == null || name.length() == 0)
			name = impBP.getEMail();
		setName(name);
		setName2(impBP.getName2());
		setDescription(impBP.getDescription());
	//	setHelp(impBP.getHelp());
		setDUNS(impBP.getDUNS());
		setTaxID(impBP.getTaxID());
		setNAICS(impBP.getNAICS());
		setC_BP_Group_ID(impBP.getC_BP_Group_ID());
	}	//	MBPartner
	
	/**
	 * Copy constructor
	 * @param copy
	 */
	public MBPartner(MBPartner copy) 
	{
		this(Env.getCtx(), copy);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 */
	public MBPartner(Properties ctx, MBPartner copy) 
	{
		this(ctx, copy, (String) null);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 * @param trxName
	 */
	public MBPartner(Properties ctx, MBPartner copy, String trxName) 
	{
		this(ctx, 0, trxName);
		copyPO(copy);
		this.m_contacts = copy.m_contacts != null ? Arrays.stream(copy.m_contacts).map(e -> {return new MUser(ctx, e, trxName);}).toArray(MUser[]::new) : null;
		this.m_locations = copy.m_locations != null ? Arrays.stream(copy.m_locations).map(e -> {return new MBPartnerLocation(ctx, e, trxName);}).toArray(MBPartnerLocation[]::new) : null;
		this.m_accounts = copy.m_accounts != null ? Arrays.stream(copy.m_accounts).map(e -> {return new MBPBankAccount(ctx, e, trxName);}).toArray(MBPBankAccount[]::new) : null;
		this.m_primaryC_BPartner_Location_ID = copy.m_primaryC_BPartner_Location_ID;
		this.m_primaryAD_User_ID = copy.m_primaryAD_User_ID;
		this.m_group = copy.m_group != null ? new MBPGroup(ctx, copy.m_group, trxName) : null;
	}

	/**
	 * @param ctx
	 * @param C_BPartner_ID
	 * @param trxName
	 * @param virtualColumns
	 */
	public MBPartner(Properties ctx, int C_BPartner_ID, String trxName, String... virtualColumns) {
		super(ctx, C_BPartner_ID, trxName, virtualColumns);
	}

	/** Users							*/
	protected MUser[]				m_contacts = null;
	/** Addressed						*/
	protected MBPartnerLocation[]	m_locations = null;
	/** BP Bank Accounts				*/
	protected MBPBankAccount[]		m_accounts = null;
	/** Prim Address					*/
	protected Integer				m_primaryC_BPartner_Location_ID = null;
	/** Prim User						*/
	protected Integer				m_primaryAD_User_ID = null;
	/** BP Group						*/
	protected MBPGroup				m_group = null;
	
	/**
	 * 	Initialize BPartner record from template
	 * 	@param AD_Client_ID client
	 * 	@return true if loaded
	 */
	protected boolean initTemplate (int AD_Client_ID)
	{
		if (AD_Client_ID == 0)
			throw new IllegalArgumentException ("Client_ID=0");

		boolean success = true;
		String sql = "SELECT * FROM C_BPartner "
			+ "WHERE C_BPartner_ID IN (SELECT C_BPartnerCashTrx_ID FROM AD_ClientInfo WHERE AD_Client_ID=?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				success = load (rs);
			else
			{
				load(0, null);
				success = false;
				log.severe ("None found");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		setStandardDefaults();
		//	Reset
		set_ValueNoCheck ("C_BPartner_ID", I_ZERO);
		setValue ("");
		setName ("");
		setName2(null);
		set_ValueNoCheck ("C_BPartner_UU", "");
		return success;
	}	//	getTemplate

	/**
	 * 	Get All Contacts
	 * 	@param reload true to reload from DB
	 *	@return contacts
	 */
	public MUser[] getContacts (boolean reload)
	{
		if (reload || m_contacts == null || m_contacts.length == 0)
			;
		else
			return m_contacts;
		//
		ArrayList<MUser> list = new ArrayList<MUser>();
		final String sql = "SELECT * FROM AD_User WHERE C_BPartner_ID=? AND IsActive = 'Y' ORDER BY AD_User_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_BPartner_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUser (getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		m_contacts = new MUser[list.size()];
		list.toArray(m_contacts);
		return m_contacts;
	}	//	getContacts

	/**
	 * 	Get specified or first Contact
	 * 	@param AD_User_ID optional user
	 *	@return contact or null
	 */
	public MUser getContact (int AD_User_ID)
	{
		MUser[] users = getContacts(false);
		if (users.length == 0)
			return null;
		for (int i = 0; AD_User_ID != 0 && i < users.length; i++)
		{
			if (users[i].getAD_User_ID() == AD_User_ID)
				return users[i];
		}
		return users[0];
	}	//	getContact
		
	/**
	 * Get All Locations (only active)
	 * @param reload true to reload from DB
	 * @return locations
	 */
	public MBPartnerLocation[] getLocations (boolean reload)
	{
		if (reload || m_locations == null || m_locations.length == 0)
			;
		else
			return m_locations;
		//
		ArrayList<MBPartnerLocation> list = new ArrayList<MBPartnerLocation>();
		final String sql = "SELECT * FROM C_BPartner_Location WHERE C_BPartner_ID=? AND IsActive='Y'"
						+ " ORDER BY C_BPartner_Location_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_BPartner_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MBPartnerLocation (getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		m_locations = new MBPartnerLocation[list.size()];
		list.toArray(m_locations);
		return m_locations;
	}	//	getLocations

	/**
	 * 	Get location for C_BPartner_Location_ID or first bill Location or first location
	 * 	@param C_BPartner_Location_ID optional explicit location
	 *	@return location or null
	 */
	public MBPartnerLocation getLocation(int C_BPartner_Location_ID)
	{
		MBPartnerLocation[] locations = getLocations(false);
		if (locations.length == 0)
			return null;
		MBPartnerLocation retValue = null;
		for (int i = 0; i < locations.length; i++)
		{
			if (locations[i].getC_BPartner_Location_ID() == C_BPartner_Location_ID)
				return locations[i];
			if (retValue == null && locations[i].isBillTo())
				retValue = locations[i];
		}
		if (retValue == null)
			return locations[0];
		return retValue;
	}	//	getLocation
	
	
	/**
	 * 	Get Bank Accounts
	 * 	@param requery true to reload from DB
	 *	@return Bank Accounts
	 */
	public MBPBankAccount[] getBankAccounts (boolean requery)
	{
		if (m_accounts != null && m_accounts.length >= 0 && !requery)	//	re-load
			return m_accounts;
		//
		ArrayList<MBPBankAccount> list = new ArrayList<MBPBankAccount>();
		String sql = "SELECT * FROM C_BP_BankAccount WHERE C_BPartner_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_BPartner_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MBPBankAccount (getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		m_accounts = new MBPBankAccount[list.size()];
		list.toArray(m_accounts);
		return m_accounts;
	}	//	getBankAccounts
	
	/**
	 *	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MBPartner[ID=")
			.append(get_ID())
			.append(",Value=").append(getValue())
			.append(",Name=").append(getName())
			.append(",Open=").append(getTotalOpenBalance())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Set Client/Org
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 */
	@Override
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg

	/** 
	 * 	Get Linked Organization.
	 * 	(is Button)
	 * 	The Business Partner is another Organization 
	 * 	for explicit Inter-Org transactions 
	 * 	@return AD_Org_ID if BP
	 *  @deprecated
	 */
	@Deprecated(forRemoval = true, since = "11")
	public int getAD_OrgBP_ID_Int() 
	{
		return getAD_OrgBP_ID();
	}	//	getAD_OrgBP_ID_Int

	/**
	 * 	Get Primary C_BPartner_Location_ID (First BillTo or First)
	 *	@return C_BPartner_Location_ID
	 */
	public int getPrimaryC_BPartner_Location_ID()
	{
		if (m_primaryC_BPartner_Location_ID == null)
		{
			MBPartnerLocation[] locs = getLocations(false);
			for (int i = 0; m_primaryC_BPartner_Location_ID == null && i < locs.length; i++)
			{
				if (locs[i].isBillTo())
				{
					setPrimaryC_BPartner_Location_ID (locs[i].getC_BPartner_Location_ID());
					break;
				}
			}
			//	get first
			if (m_primaryC_BPartner_Location_ID == null && locs.length > 0)
				setPrimaryC_BPartner_Location_ID (locs[0].getC_BPartner_Location_ID()); 
		}
		if (m_primaryC_BPartner_Location_ID == null)
			return 0;
		return m_primaryC_BPartner_Location_ID.intValue();
	}	//	getPrimaryC_BPartner_Location_ID
	
	/**
	 * 	Get Primary C_BPartner_Location (BillTo or First)
	 *	@return C_BPartner_Location
	 */
	public MBPartnerLocation getPrimaryC_BPartner_Location()
	{
		if (m_primaryC_BPartner_Location_ID == null)
		{
			m_primaryC_BPartner_Location_ID = getPrimaryC_BPartner_Location_ID();
		}
		if (m_primaryC_BPartner_Location_ID == null)
			return null;
		return new MBPartnerLocation(getCtx(), m_primaryC_BPartner_Location_ID, null);
	}	//	getPrimaryC_BPartner_Location
	
	/**
	 * 	Get Primary AD_User_ID
	 *	@return AD_User_ID or -1
	 */
	public int getPrimaryAD_User_ID()
	{
		if (m_primaryAD_User_ID == null)
		{
			MUser[] users = getContacts(false);
			if (m_primaryAD_User_ID == null && users.length > 0)
				setPrimaryAD_User_ID(users[0].getAD_User_ID());
		}
		if (m_primaryAD_User_ID == null)
			return -1;
		return m_primaryAD_User_ID.intValue();
	}	//	getPrimaryAD_User_ID

	/**
	 * 	Set Primary C_BPartner_Location_ID
	 *	@param C_BPartner_Location_ID id
	 */
	public void setPrimaryC_BPartner_Location_ID(int C_BPartner_Location_ID)
	{
		m_primaryC_BPartner_Location_ID = Integer.valueOf(C_BPartner_Location_ID);
	}	//	setPrimaryC_BPartner_Location_ID
	
	/**
	 * 	Set Primary AD_User_ID
	 *	@param AD_User_ID id
	 */
	public void setPrimaryAD_User_ID(int AD_User_ID)
	{
		m_primaryAD_User_ID = Integer.valueOf(AD_User_ID);
	}	//	setPrimaryAD_User_ID
		
	/**
	 * 	Calculate Total Open Balance and SO_CreditUsed.
	 */
	public void setTotalOpenBalance () {
		log.info("");
		BigDecimal SO_CreditUsed = null;
		BigDecimal TotalOpenBalance = null;
		//AZ Goodwill -> BF2041226 : only count completed/closed docs.
		String sql = "SELECT "
			//	SO Credit Used
			+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0), "
			//	Balance (incl. unallocated payments)
			+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)*i.MultiplierAP) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0) - "
			+ "COALESCE((SELECT SUM(currencyBase(Paymentavailable(p.C_Payment_ID),p.C_Currency_ID,p.DateTrx,p.AD_Client_ID,p.AD_Org_ID)) FROM C_Payment_v p "
				+ "WHERE p.C_BPartner_ID=bp.C_BPartner_ID AND p.IsAllocated='N'"
				+ " AND p.C_Charge_ID IS NULL AND p.DocStatus IN ('CO','CL')),0) "
			+ "FROM C_BPartner bp "
			+ "WHERE C_BPartner_ID=?";		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_BPartner_ID());
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				SO_CreditUsed = rs.getBigDecimal(1);
				TotalOpenBalance = rs.getBigDecimal(2);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		if (SO_CreditUsed != null)
			super.setSO_CreditUsed (SO_CreditUsed);
		if (TotalOpenBalance != null)
			super.setTotalOpenBalance(TotalOpenBalance);
		setSOCreditStatus();
	}	//	setTotalOpenBalance

	/**
	 * 	Calculate Actual Life Time Invoiced Value from DB
	 */
	public void setActualLifeTimeValue()
	{
		BigDecimal ActualLifeTimeValue = null;
		//AZ Goodwill -> BF2041226 : only count completed/closed docs.
		String sql = "SELECT "
			+ "COALESCE ((SELECT SUM(currencyBase(i.GrandTotal,i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.DocStatus IN ('CO','CL')),0) " 
			+ "FROM C_BPartner bp "
			+ "WHERE C_BPartner_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_BPartner_ID());
			rs = pstmt.executeQuery ();
			if (rs.next ())
				ActualLifeTimeValue = rs.getBigDecimal(1);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (ActualLifeTimeValue != null)
			super.setActualLifeTimeValue (ActualLifeTimeValue);
	}	//	setActualLifeTimeValue
	
	/**
	 * Update Credit Status for sales transaction
	 */
	public void setSOCreditStatus ()
	{
		BigDecimal creditLimit = getSO_CreditLimit(); 
		//	Nothing to do
		if (SOCREDITSTATUS_NoCreditCheck.equals(getSOCreditStatus())
			|| SOCREDITSTATUS_CreditStop.equals(getSOCreditStatus())
			|| Env.ZERO.compareTo(creditLimit) == 0)
			return;

		//	Above Credit Limit
		if (creditLimit.compareTo(getTotalOpenBalance()) < 0)
			setSOCreditStatus(SOCREDITSTATUS_CreditHold);
		else
		{
			//	Above Watch Limit
			BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio());
			if (watchAmt.compareTo(getTotalOpenBalance()) < 0)
				setSOCreditStatus(SOCREDITSTATUS_CreditWatch);
			else	//	is OK
				setSOCreditStatus (SOCREDITSTATUS_CreditOK);
		}
	}	//	setSOCreditStatus
		
	/**
	 * 	Get SO CreditStatus with additional amount
	 * 	@param additionalAmt additional amount in Accounting Currency
	 *	@return simulated credit status
	 */
	public String getSOCreditStatus (BigDecimal additionalAmt)
	{
		if (additionalAmt == null || additionalAmt.signum() == 0)
			return getSOCreditStatus();
		//
		BigDecimal creditLimit = getSO_CreditLimit(); 
		//	Nothing to do
		if (SOCREDITSTATUS_NoCreditCheck.equals(getSOCreditStatus())
			|| SOCREDITSTATUS_CreditStop.equals(getSOCreditStatus())
			|| Env.ZERO.compareTo(creditLimit) == 0)
			return getSOCreditStatus();

		//	Above (reduced) Credit Limit
		creditLimit = creditLimit.subtract(additionalAmt);
		if (creditLimit.compareTo(getTotalOpenBalance()) < 0)
			return SOCREDITSTATUS_CreditHold;
		
		//	Above Watch Limit
		BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio());
		if (watchAmt.compareTo(getTotalOpenBalance()) < 0)
			return SOCREDITSTATUS_CreditWatch;
		
		//	is OK
		return SOCREDITSTATUS_CreditOK;
	}	//	getSOCreditStatus
	
	/**
	 * 	Get Credit Watch Ratio
	 *	@return BP Group ratio or 0.9
	 */
	public BigDecimal getCreditWatchRatio()
	{
		return getBPGroup().getCreditWatchRatio();
	}	//	getCreditWatchRatio
		
	/**
	 * 	Credit Status is Stop or Hold.
	 *	@return true if Stop/Hold
	 */
	public boolean isCreditStopHold()
	{
		String status = getSOCreditStatus();
		return SOCREDITSTATUS_CreditStop.equals(status)
			|| SOCREDITSTATUS_CreditHold.equals(status);
	}	//	isCreditStopHold
	
	/**
	 * 	Get BP Group
	 *	@return group
	 */
	public MBPGroup getBPGroup()
	{
		if (m_group == null)
		{
			if (getC_BP_Group_ID() == 0)
				m_group = MBPGroup.getDefault(getCtx());
			else
				m_group = MBPGroup.getCopy(getCtx(), getC_BP_Group_ID(), get_TrxName());
		}
		return m_group;
	}	//	getBPGroup

	/**
	 * 	Set BP Group
	 *	@param group group
	 */
	public void setBPGroup(MBPGroup group)
	{
		m_group = group;
		if (m_group == null)
			return;
		setC_BP_Group_ID(m_group.getC_BP_Group_ID());
		if (m_group.getC_Dunning_ID() != 0)
			setC_Dunning_ID(m_group.getC_Dunning_ID());
		if (m_group.getM_PriceList_ID() != 0)
			setM_PriceList_ID(m_group.getM_PriceList_ID());
		if (m_group.getPO_PriceList_ID() != 0)
			setPO_PriceList_ID(m_group.getPO_PriceList_ID());
		if (m_group.getM_DiscountSchema_ID() != 0)
			setM_DiscountSchema_ID(m_group.getM_DiscountSchema_ID());
		if (m_group.getPO_DiscountSchema_ID() != 0)
			setPO_DiscountSchema_ID(m_group.getPO_DiscountSchema_ID());
	}	//	setBPGroup

	/**
	 * 	Get PriceList ID
	 *	@return BP M_PriceList_ID or BP Group M_PriceList_ID 
	 */
	public int getM_PriceList_ID ()
	{
		int ii = super.getM_PriceList_ID();
		if (ii == 0)
			ii = getBPGroup().getM_PriceList_ID();
		return ii;
	}	//	getM_PriceList_ID
	
	/**
	 * 	Get PO PriceList ID
	 *	@return BP PO price list id or BP Group PO price list id
	 */
	public int getPO_PriceList_ID ()
	{
		int ii = super.getPO_PriceList_ID();
		if (ii == 0)
			ii = getBPGroup().getPO_PriceList_ID();
		return ii;
	}	//
	
	/**
	 * 	Get DiscountSchema id
	 *	@return Discount Schema id
	 */
	public int getM_DiscountSchema_ID ()
	{
		int ii = super.getM_DiscountSchema_ID ();
		if (ii == 0)
			ii = getBPGroup().getM_DiscountSchema_ID();
		return ii;
	}	//	getM_DiscountSchema_ID
	
	/**
	 * 	Get PO DiscountSchema id
	 *	@return po discount schema id
	 */
	public int getPO_DiscountSchema_ID ()
	{
		int ii = super.getPO_DiscountSchema_ID ();
		if (ii == 0)
			ii = getBPGroup().getPO_DiscountSchema_ID();
		return ii;
	}	//	getPO_DiscountSchema_ID
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		// Set default from BP Group (for new record or if C_BP_Group_ID has change)
		if (newRecord || is_ValueChanged("C_BP_Group_ID"))
		{
			MBPGroup grp = getBPGroup();
			if (grp == null)
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@:  @C_BP_Group_ID@"));
				return false;
			}
			setBPGroup(grp);	//	setDefaults
		}
		return true;
	}	//	beforeSave
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			//	Create Tree Record
			insert_Tree(MTree_Base.TREETYPE_BPartner);
			//	Create Accounting Record
			StringBuilder msgacc = new StringBuilder("p.C_BP_Group_ID=")
					.append(getC_BP_Group_ID() > MTable.MAX_OFFICIAL_ID && Env.isLogMigrationScript(get_TableName())
							? "toRecordId('C_BP_Group',"+DB.TO_STRING(MBPGroup.get(getC_BP_Group_ID()).getC_BP_Group_UU())+")"
							: getC_BP_Group_ID());
			insert_Accounting("C_BP_Customer_Acct", "C_BP_Group_Acct", msgacc.toString());
			insert_Accounting("C_BP_Vendor_Acct", "C_BP_Group_Acct",msgacc.toString());
		}
		if (newRecord || is_ValueChanged(COLUMNNAME_Value))
			update_Tree(MTree_Base.TREETYPE_BPartner);

		//	Value/Name change, update Combination and Description of C_ValidCombination
		if (!newRecord 
			&& (is_ValueChanged("Value") || is_ValueChanged("Name"))){
			StringBuilder msgacc = new StringBuilder("C_BPartner_ID=").append(getC_BPartner_ID());
			MAccount.updateValueDescription(getCtx(), msgacc.toString(), get_TrxName());
		}
		return success;
	}	//	afterSave

	@Override
	protected boolean afterDelete (boolean success)
	{
		// Delete tree record
		if (success)
			delete_Tree(MTree_Base.TREETYPE_BPartner);
		return success;
	}	//	afterDelete

	@Override
	protected boolean postDelete() {
		if (getLogo_ID() > 0) {
			MImage img = new MImage(getCtx(), getLogo_ID(), get_TrxName());
			if (!img.delete(true)) {
				log.warning("Associated image could not be deleted for bpartner - AD_Image_ID=" + getLogo_ID());
				return false;
			}
		}
		return true;
	}

	@Override
	public MBPartner markImmutable() {
		if (is_Immutable())
			return this;

		makeImmutable();
		return this;
	}

}	//	MBPartner
