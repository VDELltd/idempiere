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
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_Warehouse
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="M_Warehouse")
public class X_M_Warehouse extends PO implements I_M_Warehouse, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250516L;

    /** Standard Constructor */
    public X_M_Warehouse (Properties ctx, int M_Warehouse_ID, String trxName)
    {
      super (ctx, M_Warehouse_ID, trxName);
      /** if (M_Warehouse_ID == 0)
        {
			setC_Location_ID (0);
			setIsDisableInventoryPopup (false);
// N
			setIsDisallowNegativeInv (false);
// N
			setM_Warehouse_ID (0);
			setName (null);
			setSeparator (null);
// *
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_M_Warehouse (Properties ctx, int M_Warehouse_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, M_Warehouse_ID, trxName, virtualColumns);
      /** if (M_Warehouse_ID == 0)
        {
			setC_Location_ID (0);
			setIsDisableInventoryPopup (false);
// N
			setIsDisallowNegativeInv (false);
// N
			setM_Warehouse_ID (0);
			setName (null);
			setSeparator (null);
// *
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_M_Warehouse (Properties ctx, String M_Warehouse_UU, String trxName)
    {
      super (ctx, M_Warehouse_UU, trxName);
      /** if (M_Warehouse_UU == null)
        {
			setC_Location_ID (0);
			setIsDisableInventoryPopup (false);
// N
			setIsDisallowNegativeInv (false);
// N
			setM_Warehouse_ID (0);
			setName (null);
			setSeparator (null);
// *
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_M_Warehouse (Properties ctx, String M_Warehouse_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, M_Warehouse_UU, trxName, virtualColumns);
      /** if (M_Warehouse_UU == null)
        {
			setC_Location_ID (0);
			setIsDisableInventoryPopup (false);
// N
			setIsDisallowNegativeInv (false);
// N
			setM_Warehouse_ID (0);
			setName (null);
			setSeparator (null);
// *
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_Warehouse (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
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
      StringBuilder sb = new StringBuilder ("X_M_Warehouse[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public I_C_Location getC_Location() throws RuntimeException
	{
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_ID)
			.getPO(getC_Location_ID(), get_TrxName());
	}

	/** Set Address.
		@param C_Location_ID Location or Address
	*/
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1)
			set_Value (COLUMNNAME_C_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Disable Insufficient Inventory Popup.
		@param IsDisableInventoryPopup Disable Insufficient Inventory Popup
	*/
	public void setIsDisableInventoryPopup (boolean IsDisableInventoryPopup)
	{
		set_Value (COLUMNNAME_IsDisableInventoryPopup, Boolean.valueOf(IsDisableInventoryPopup));
	}

	/** Get Disable Insufficient Inventory Popup.
		@return Disable Insufficient Inventory Popup	  */
	public boolean isDisableInventoryPopup()
	{
		Object oo = get_Value(COLUMNNAME_IsDisableInventoryPopup);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Disallow Negative Inventory.
		@param IsDisallowNegativeInv Negative Inventory is not allowed in this warehouse
	*/
	public void setIsDisallowNegativeInv (boolean IsDisallowNegativeInv)
	{
		set_Value (COLUMNNAME_IsDisallowNegativeInv, Boolean.valueOf(IsDisallowNegativeInv));
	}

	/** Get Disallow Negative Inventory.
		@return Negative Inventory is not allowed in this warehouse
	  */
	public boolean isDisallowNegativeInv()
	{
		Object oo = get_Value(COLUMNNAME_IsDisallowNegativeInv);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Transit.
		@param IsInTransit Movement is in transit
	*/
	public void setIsInTransit (boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, Boolean.valueOf(IsInTransit));
	}

	/** Get In Transit.
		@return Movement is in transit
	  */
	public boolean isInTransit()
	{
		Object oo = get_Value(COLUMNNAME_IsInTransit);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Locator getM_ReserveLocator() throws RuntimeException
	{
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_ID)
			.getPO(getM_ReserveLocator_ID(), get_TrxName());
	}

	/** Set Reservation Locator.
		@param M_ReserveLocator_ID Reservation Locator (just for reporting purposes)
	*/
	public void setM_ReserveLocator_ID (int M_ReserveLocator_ID)
	{
		if (M_ReserveLocator_ID < 1)
			set_Value (COLUMNNAME_M_ReserveLocator_ID, null);
		else
			set_Value (COLUMNNAME_M_ReserveLocator_ID, Integer.valueOf(M_ReserveLocator_ID));
	}

	/** Get Reservation Locator.
		@return Reservation Locator (just for reporting purposes)
	  */
	public int getM_ReserveLocator_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReserveLocator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_WarehouseSource() throws RuntimeException
	{
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_ID)
			.getPO(getM_WarehouseSource_ID(), get_TrxName());
	}

	/** Set Source Warehouse.
		@param M_WarehouseSource_ID Optional Warehouse to replenish from
	*/
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID)
	{
		if (M_WarehouseSource_ID < 1)
			set_Value (COLUMNNAME_M_WarehouseSource_ID, null);
		else
			set_Value (COLUMNNAME_M_WarehouseSource_ID, Integer.valueOf(M_WarehouseSource_ID));
	}

	/** Get Source Warehouse.
		@return Optional Warehouse to replenish from
	  */
	public int getM_WarehouseSource_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_WarehouseSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Warehouse.
		@param M_Warehouse_ID Storage Warehouse and Service Point
	*/
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Warehouse_UU.
		@param M_Warehouse_UU M_Warehouse_UU
	*/
	public void setM_Warehouse_UU (String M_Warehouse_UU)
	{
		set_Value (COLUMNNAME_M_Warehouse_UU, M_Warehouse_UU);
	}

	/** Get M_Warehouse_UU.
		@return M_Warehouse_UU	  */
	public String getM_Warehouse_UU()
	{
		return (String)get_Value(COLUMNNAME_M_Warehouse_UU);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Replenishment Class.
		@param ReplenishmentClass Custom class to calculate Quantity to Order
	*/
	public void setReplenishmentClass (String ReplenishmentClass)
	{
		set_Value (COLUMNNAME_ReplenishmentClass, ReplenishmentClass);
	}

	/** Get Replenishment Class.
		@return Custom class to calculate Quantity to Order
	  */
	public String getReplenishmentClass()
	{
		return (String)get_Value(COLUMNNAME_ReplenishmentClass);
	}

	/** Set Element Separator.
		@param Separator Element Separator
	*/
	public void setSeparator (String Separator)
	{
		set_Value (COLUMNNAME_Separator, Separator);
	}

	/** Get Element Separator.
		@return Element Separator
	  */
	public String getSeparator()
	{
		return (String)get_Value(COLUMNNAME_Separator);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}