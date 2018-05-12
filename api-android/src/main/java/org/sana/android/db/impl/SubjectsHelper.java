/**
 * Copyright (c) 2013, Sana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Sana nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL Sana BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.sana.android.db.impl;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import org.sana.android.db.TableHelper;

import org.sana.android.provider.BaseContract;
import org.sana.android.provider.Models;
import org.sana.android.provider.Patients.Contract;
import org.sana.core.Subject;

/**
 * A database table helper for a table of subjects.
 * 
 * @author Sana Development
 *
 */
public class SubjectsHelper extends TableHelper<Subject>{
	public static final String TAG = SubjectsHelper.class.getSimpleName();

	private static final SubjectsHelper HELPER = new SubjectsHelper();
	
	/**
	 * Gets the singleton instance of this class.
	 * 
	 * @return An instance of this class.
	 */
	public static SubjectsHelper getInstance(){
		return HELPER;
	}
	
	protected SubjectsHelper(){
		super(Subject.class);
	}
	
	/* (non-Javadoc)
	 * @see org.sana.android.db.InsertHelper#onInsert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public ContentValues onInsert(ContentValues values) {
        if(!values.containsKey(BaseContract.SYNCH)){
            values.put(BaseContract.SYNCH, Models.Synch.NEW);
        }
		return super.onInsert(values);
	}

	/* (non-Javadoc)
	 * @see org.sana.android.db.UpdateHelper#onUpdate(java.lang.String, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public ContentValues onUpdate(Uri uri, ContentValues values) {
        if(!values.containsKey(BaseContract.SYNCH)){
            values.put(BaseContract.SYNCH, Models.Synch.MODIFIED);
        }
		return super.onUpdate(uri, values);
	}

	/* (non-Javadoc)
	 * @see org.sana.android.db.CreateHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public String onCreate() {
		Log.i(TAG, "onCreate()");
		return "CREATE TABLE " + getTable() + " ("
                + Contract._ID 				+ " INTEGER PRIMARY KEY,"
		        + Contract.UUID 			+ " TEXT NOT NULL,"
                + Contract.CREATED 			+ " DATE,"
                + Contract.MODIFIED 		+ " DATE,"
                + BaseContract.SYNCH + " INTEGER DEFAULT '-1', "
                + Contract.PATIENT_ID 		+ " TEXT,"
                + Contract.GIVEN_NAME 		+ " TEXT NOT NULL,"
                + Contract.FAMILY_NAME		+ " TEXT NOT NULL,"
                + Contract.GENDER 			+ " TEXT NOT NULL,"
				+ Contract.PNUMBER 			+ " TEXT,"
				+ Contract.HOLDER_pNUMBER 	+ " TEXT,"
				+ Contract.LMD		 		+ " DATE,"
				+ Contract.MARITAL_STATUS	+ " TEXT,"
				+ Contract.EDUCATION_LEVEL	+ " TEXT,"
				+ Contract.CONTRACEPTIVE_USE + " TEXT,"
				+ Contract.ANC_STATUS 		+ " TEXT,"
				+ Contract.ANC_VISIT 		+ " DATE,"
				+ Contract.AMBULANCE_NEED 		+ " TEXT,"
				+ Contract.AMBULANCE_RESPONSE 	+ " TEXT,"
				+ Contract.EDD		 		+ " DATE,"
				+ Contract.RECEIVE_SMS 		+ " TEXT,"
				+ Contract.FOLLOW_UP 		+ " TEXT,"
				+ Contract.CUG_STATUS 		+ " TEXT,"
				+ Contract.COMMENT	 		+ " TEXT,"
				+ Contract.BLEEDING 		+ " TEXT,"
				+ Contract.FEVER	 		+ " TEXT,"
				+ Contract.SWOLLEN_FEET		+ " TEXT,"
				+ Contract.BLURRED_VISION	+ " TEXT,"
                + Contract.IMAGE 			+ " TEXT,"
                + Contract.STATE 			+ " INTEGER DEFAULT '-1',"
                + Contract.DOB 				+ " DATE, "
                + Contract.LOCATION			+ " TEXT,"
				+ Contract.DISTRICT			+ " TEXT,"
				+ Contract.COUNTY			+ " TEXT,"
				+ Contract.SUBCOUNTY		+ " TEXT,"
				+ Contract.PARISH			+ " TEXT,"
				+ Contract.VILLAGE			+ " TEXT,"
                + Contract.ADDRESS_ONE 		+ " TEXT,"
                + Contract.ADDRESS_TWO 		+ " TEXT,"
                + Contract.ADDRESS_THREE 	+ " TEXT,"
                + Contract.ADDRESS_FOUR 	+ " TEXT,"
                + Contract.CONTACT_ONE 		+ " TEXT,"
                + Contract.CONTACT_TWO 		+ " TEXT,"
                + Contract.CONTACT_THREE 	+ " TEXT,"
                + Contract.CONTACT_FOUR 	+ " TEXT"

                //TODO update db, replace line above, and uncomment
                //+ Contract.CONTACT_FOUR + " TEXT,"
                //+ Contract.CONFIRMED + " BOOLEAN,"
                //+ Contract.DOB_ESTIMATED + " BOOLEAN,"
        + ");";
		
	}

	/* (non-Javadoc)
	 * @see org.sana.android.db.UpgradeHelper#onUpgrade(int, int)
	 */
	@Override
	public String onUpgrade(int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade()");
		String sql = null;
		if (newVersion > oldVersion) {
            StringBuilder sqlBuilder = new StringBuilder();
            if (oldVersion == 2 && newVersion == 3) {
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.AMBULANCE_NEED + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.AMBULANCE_RESPONSE + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.VILLAGE + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.DISTRICT + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.COUNTY + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.SUBCOUNTY + " TEXT;");
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " + Contract.PARISH + " TEXT;");
            }
            if (newVersion == 8) {
                sqlBuilder.append("ALTER TABLE " + getTable() + " ADD COLUMN " +
                        BaseContract.SYNCH + " INTEGER DEFAULT '-1';");

            }
            sql = sqlBuilder.toString();
        }
		return sql;
	}
	
}
