/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for A_Depreciation_Build
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="A_Depreciation_Build")
public class X_A_Depreciation_Build extends PO implements I_A_Depreciation_Build, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_A_Depreciation_Build (Properties ctx, int A_Depreciation_Build_ID, String trxName)
    {
      super (ctx, A_Depreciation_Build_ID, trxName);
      /** if (A_Depreciation_Build_ID == 0)
        {
			setA_Depreciation_Build_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_A_Depreciation_Build (Properties ctx, int A_Depreciation_Build_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, A_Depreciation_Build_ID, trxName, virtualColumns);
      /** if (A_Depreciation_Build_ID == 0)
        {
			setA_Depreciation_Build_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_A_Depreciation_Build (Properties ctx, String A_Depreciation_Build_UU, String trxName)
    {
      super (ctx, A_Depreciation_Build_UU, trxName);
      /** if (A_Depreciation_Build_UU == null)
        {
			setA_Depreciation_Build_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_A_Depreciation_Build (Properties ctx, String A_Depreciation_Build_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, A_Depreciation_Build_UU, trxName, virtualColumns);
      /** if (A_Depreciation_Build_UU == null)
        {
			setA_Depreciation_Build_ID (0);
        } */
    }

    /** Load Constructor */
    public X_A_Depreciation_Build (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_A_Depreciation_Build[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Build Depreciation.
		@param A_Depreciation_Build_ID Build Depreciation
	*/
	public void setA_Depreciation_Build_ID (int A_Depreciation_Build_ID)
	{
		if (A_Depreciation_Build_ID < 1)
			set_ValueNoCheck (COLUMNNAME_A_Depreciation_Build_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_A_Depreciation_Build_ID, Integer.valueOf(A_Depreciation_Build_ID));
	}

	/** Get Build Depreciation.
		@return Build Depreciation	  */
	public int getA_Depreciation_Build_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Build_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Depreciation_Build_ID()));
    }

	/** Set A_Depreciation_Build_UU.
		@param A_Depreciation_Build_UU A_Depreciation_Build_UU
	*/
	public void setA_Depreciation_Build_UU (String A_Depreciation_Build_UU)
	{
		set_Value (COLUMNNAME_A_Depreciation_Build_UU, A_Depreciation_Build_UU);
	}

	/** Get A_Depreciation_Build_UU.
		@return A_Depreciation_Build_UU	  */
	public String getA_Depreciation_Build_UU()
	{
		return (String)get_Value(COLUMNNAME_A_Depreciation_Build_UU);
	}

	public org.compiere.model.I_A_Asset getA_End_Asset() throws RuntimeException
	{
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_ID)
			.getPO(getA_End_Asset_ID(), get_TrxName());
	}

	/** Set To Asset.
		@param A_End_Asset_ID To Asset
	*/
	public void setA_End_Asset_ID (int A_End_Asset_ID)
	{
		if (A_End_Asset_ID < 1)
			set_Value (COLUMNNAME_A_End_Asset_ID, null);
		else
			set_Value (COLUMNNAME_A_End_Asset_ID, Integer.valueOf(A_End_Asset_ID));
	}

	/** Get To Asset.
		@return To Asset	  */
	public int getA_End_Asset_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_End_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_A_Asset getA_Start_Asset() throws RuntimeException
	{
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_ID)
			.getPO(getA_Start_Asset_ID(), get_TrxName());
	}

	/** Set From Asset.
		@param A_Start_Asset_ID From Asset
	*/
	public void setA_Start_Asset_ID (int A_Start_Asset_ID)
	{
		if (A_Start_Asset_ID < 1)
			set_Value (COLUMNNAME_A_Start_Asset_ID, null);
		else
			set_Value (COLUMNNAME_A_Start_Asset_ID, Integer.valueOf(A_Start_Asset_ID));
	}

	/** Get From Asset.
		@return From Asset	  */
	public int getA_Start_Asset_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Start_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_ID)
			.getPO(getC_Period_ID(), get_TrxName());
	}

	/** Set Period.
		@param C_Period_ID Period of the Calendar
	*/
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1)
			set_Value (COLUMNNAME_C_Period_ID, null);
		else
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct Accounting Date
	*/
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Document Date.
		@param DateDoc Date of the Document
	*/
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Period No.
		@param PeriodNo Unique Period Number
	*/
	public void setPeriodNo (int PeriodNo)
	{
		set_Value (COLUMNNAME_PeriodNo, Integer.valueOf(PeriodNo));
	}

	/** Get Period No.
		@return Unique Period Number
	  */
	public int getPeriodNo()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PeriodNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PostingType AD_Reference_ID=125 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Set Posting Type.
		@param PostingType The type of posted amount for the transaction
	*/
	public void setPostingType (String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get Posting Type.
		@return The type of posted amount for the transaction
	  */
	public String getPostingType()
	{
		return (String)get_Value(COLUMNNAME_PostingType);
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}