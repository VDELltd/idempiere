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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_OrderTax
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_OrderTax")
public class X_C_OrderTax extends PO implements I_C_OrderTax, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_C_OrderTax (Properties ctx, int C_OrderTax_ID, String trxName)
    {
      super (ctx, C_OrderTax_ID, trxName);
      /** if (C_OrderTax_ID == 0)
        {
			setC_Order_ID (0);
			setC_Tax_ID (0);
			setIsTaxIncluded (false);
			setProcessed (false);
			setTaxAmt (Env.ZERO);
			setTaxBaseAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderTax (Properties ctx, int C_OrderTax_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_OrderTax_ID, trxName, virtualColumns);
      /** if (C_OrderTax_ID == 0)
        {
			setC_Order_ID (0);
			setC_Tax_ID (0);
			setIsTaxIncluded (false);
			setProcessed (false);
			setTaxAmt (Env.ZERO);
			setTaxBaseAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderTax (Properties ctx, String C_OrderTax_UU, String trxName)
    {
      super (ctx, C_OrderTax_UU, trxName);
      /** if (C_OrderTax_UU == null)
        {
			setC_Order_ID (0);
			setC_Tax_ID (0);
			setIsTaxIncluded (false);
			setProcessed (false);
			setTaxAmt (Env.ZERO);
			setTaxBaseAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderTax (Properties ctx, String C_OrderTax_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_OrderTax_UU, trxName, virtualColumns);
      /** if (C_OrderTax_UU == null)
        {
			setC_Order_ID (0);
			setC_Tax_ID (0);
			setIsTaxIncluded (false);
			setProcessed (false);
			setTaxAmt (Env.ZERO);
			setTaxBaseAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_OrderTax (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org
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
      StringBuilder sb = new StringBuilder ("X_C_OrderTax[")
        .append(get_UUID()).append("]");
      return sb.toString();
    }

	/** Set C_OrderTax_UU.
		@param C_OrderTax_UU C_OrderTax_UU
	*/
	public void setC_OrderTax_UU (String C_OrderTax_UU)
	{
		set_Value (COLUMNNAME_C_OrderTax_UU, C_OrderTax_UU);
	}

	/** Get C_OrderTax_UU.
		@return C_OrderTax_UU	  */
	public String getC_OrderTax_UU()
	{
		return (String)get_Value(COLUMNNAME_C_OrderTax_UU);
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_ID)
			.getPO(getC_Order_ID(), get_TrxName());
	}

	/** Set Order.
		@param C_Order_ID Order
	*/
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_TaxProvider getC_TaxProvider() throws RuntimeException
	{
		return (org.compiere.model.I_C_TaxProvider)MTable.get(getCtx(), org.compiere.model.I_C_TaxProvider.Table_ID)
			.getPO(getC_TaxProvider_ID(), get_TrxName());
	}

	/** Set Tax Provider.
		@param C_TaxProvider_ID Tax Provider
	*/
	public void setC_TaxProvider_ID (int C_TaxProvider_ID)
	{
		if (C_TaxProvider_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_TaxProvider_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_TaxProvider_ID, Integer.valueOf(C_TaxProvider_ID));
	}

	/** Get Tax Provider.
		@return Tax Provider	  */
	public int getC_TaxProvider_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxProvider_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
	{
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_ID)
			.getPO(getC_Tax_ID(), get_TrxName());
	}

	/** Set Tax.
		@param C_Tax_ID Tax identifier
	*/
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Price includes Tax.
		@param IsTaxIncluded Tax is included in the price 
	*/
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Price includes Tax.
		@return Tax is included in the price 
	  */
	public boolean isTaxIncluded()
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Tax Amount.
		@param TaxAmt Tax Amount for a document
	*/
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax base Amount.
		@param TaxBaseAmt Base for calculating the tax amount
	*/
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Tax base Amount.
		@return Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}