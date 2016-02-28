package com.example.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.example.dao.DDIPairDao;
import com.example.dao.ObjectItemDao;
import com.example.dao.PercipitantItemDao;
import com.example.model.DDIPair;
import com.example.model.ObjectClass;
import com.example.model.ObjectItem;
import com.example.model.PercipitantClass;
import com.example.model.PercipitantItem;
import com.example.model.Status;

public class DBInit {
	private DDIPairDao dao;

	private static String[] ddiIds = new String[] { "3", "4", "6", "8", "11",
			"16", "20", "21", "22", "23", "25", "27", "28", "30", "31" };
	static String[] ddis = new String[] {
			"Amphetamine and derivatives-MAO inhibitors",
			"Atazanavire-gastric pH alkalizing agents (proton pump inhibitors (PPIs) + H2 blockers)",
			"Febuxostat-azathioprine/ mercaptopurine",
			"Fluoxetine-MAO inhibitors", "Irinotecan-ketoconazole",
			"Narcotic analgesics-MAO inhibitors",
			"Tricyclic antidepressants (TCAs)-Selegiline",
			"QT prolonging agents-QT prolonging agents", "Ramelton-ﬂuvoxamine",
			"Rifampin-ritonavir",
			"HMG Co-A reductase inhibitors Protease inhibitors",
			"Telithromycin-ergot alkaloids and derivatives",
			"Tizanidine-ciproﬂoxacin", "Tranylcypromine-procarbazine",
			"Triptans-MAO inhibitors" };

	// Object Class Names
	private static String[] objectClassNames = new String[] {
			"Amphetamine derivatives", "Atazanavir", "Febuxostat",
			"Selective serotonin reuptake inhibitors (SSRIs)", "Irinotecan",
			"Narcotic analgesics", "Tricyclic antidepressants (TCAs)",
			"QT prolonging agents", "Ramelteon", "Strong CYP3A4 inducers",
			"HMG Co-A reductase inhibitors", "CYP3A4 inhibitors", "Tizanidine",
			"Tranylcypromine", "Triptans" };

	// Precipitant Class Names
	private static String[] percipitantClassNames = new String[] {
			"MAO inhibitors", "Proton pump inhibitors (PPIs)",
			"Azathioprine and mercaptopurine",
			"Monoamine oxidase (MAO) inhibitors", "CYP3A4 inhibitors",
			"MAO inhibitors", "MAO inhibitors", "QT prolonging agents",
			"Specific CYP1A2 inhibitors", "Protease inhibitors",
			"CYP3A4 inhibitors", "Ergot alkaloids and derivatives",
			"CYP 1A2 inhibitors", "Procarbazine",
			"Monoamine oxidase (MAO) inhibitors" };

	// Amphetamine and derivatives-MAO inhibitors
	private static String[] amphetamineObjectItems = new String[] {
			"Dexmethylphenidate", "Dextroamphetamine", "Methylphenidate",
			"Lisdexamefetamine", "Methamphetamine", "Phendimetrazine",
			"Pseudoephedrine", "Amphetamine", "Benzphetamine",
			"Diethylpropion", "Phentermine", "Atomoxetine" };

	private static String[] amphetaminePercipitantItem = new String[] {
			"Tranylcypromine", "Phenelzine", "Isocarboxazid", "Procarbazine",
			"Selegiline" };

	// Atazanavire-gastric pH alkalizing agents (proton pump inhibitors (PPIs) +
	// H2 blockers)
	private static String[] protonPumpPercipitantItem = new String[] {
			"Omeprazole", "Lansoprazole", "Pantoprazole", "Rabeprazole",
			"Esmoprazole" };
	private static String[] atazanavirObjectItem = new String[] { "Atazanavir" };

	// Febuxostat-azathioprine/ mercaptopurine
	private static String[] febuxostatObjectItem = new String[] { "Febuxostat" };
	private static String[] azathioprinePercipitantItem = new String[] {
			"Azathioprine", "Mercaptopurine" };

	// Fluoxetine-MAO inhibitors
	private static String[] fluoxetineObjectItem = new String[] { "Fluoxetine",
			"Paroxetine", "Citalopram", "Escitalopram", "Sertraline",
			"Fluvoxamine", "Duloxetine", "Nefazodone", "Desvenlafaxine",
			"Milnacipran", "Venlafaxine" };

	// Irinotecan-ketoconazole
	private static String[] irinotecanItems = new String[] { "Irinotecan" };
	private static String[] ketoconazoleItems = new String[] { "Ritonavir",
			"Nelfinavir", "Atazanavir", "Indinavir", "Saquinavir",
			"Amprenavir", "Darunavir", "Lopinavir", "Tipranavir",
			"Fosamprenavir", "Saquinavir", "Clarithromycin", "Erythromycin",
			"Telithromycin", "Amiodarone", "Verapamil", "Ketoconazole",
			"Itraconazole", "Fluconazole", "Voriconazole", "Nefazodone",
			"Aprepitant", "Cimetidine" };

	// Narcotic analgesics-MAO inhibitors
	private static String[] narcoticAnalgesics = new String[] { "Meperidine",
			"Methadone", "Tapentadol", "Fentanyl", "Tramadol",
			"Dextromethorphan" };

	// Tricyclic antidepressants (TCAs)-Selegiline
	private static String[] tricyclicObjectItem = new String[] { "Tricyclic antidepressants (TCAs)" };

	// QT prolonging agents-QT prolonging agents
	private static String[] qtObjectItem = new String[] { "QT prolonging agents" };
	private static String[] qtPercipitantItem = new String[] { "QT prolonging agents" };

	// Ramelton-ﬂuvoxamine
	private static String[] ramelteonObjectItem = new String[] { "Ramelteon" };
	private static String[] fluvoxaminePercipitantItem = new String[] {
			"Fluvoxamine", "Amiodarone", "Ticlopidine", "Ciprofloxacin" };

	// Rifampin-ritonavir
	private static String[] rifampinObjectItem = new String[] { "Bosentan",
			"Rifapentine", "Carbamazepine", "Rifabutin", "Rifampin",
			"St John’s wort" };
	private static String[] ritonavirPercipitantItem = new String[] {
			"Ritonavir", "Nelfinavir", "Atazanavir", "Indinavir", "Saquinavir",
			"Amprenavir", "Darunavir", "Lopinavir", "Tipranavir" };

	// HMG Co-A reductase inhibitors Protease inhibitors
	private static String[] hmgObjectItem = new String[] { "Simvastatin",
			"Lovastatin" };
	private static String[] hmgPercipitantItem = new String[] { "Indinavir",
			"Saquinavir", "Tipranavir", "Ritonavir", "Nelfinavir",
			"Atazanavir", "Amprenavir", "Darunavir", "Lopinavir",
			"Clarithromycin", "Erythromycin", "Telithromycin", "Amiodarone",
			"Verapamil", "Diltiazem", "Ketoconazole", "Itraconazole",
			"Fluconazole", "Voreconazole", "Nefazodone" };

	// Telithromycin-ergot alkaloids and derivatives
	private static String[] telithromycinObjectItem = new String[] {
			"Indinavir", "Saquinavir", "Tipranavir", "Ritonavir", "Nelfinavir",
			"Atazanavir", "Amprenavir", "Darunavir", "Lopinavir",
			"Clarithromycin", "Erythromycin", "Telithromycin", "Ketoconazole",
			"Itraconazole", "Voreconazole" };
	private static String[] telithromycinPercipitantItem = new String[] {
			"Ergotamine", "Methylergonovine", "Dihydroergotamine", "Ergonovine" };

	// Tizanidine-ciproﬂoxacin
	private static String[] tizanidineObjectItem = new String[] { "Tizanidine" };
	private static String[] tizanidinePercipitantItem = new String[] {
			"Ciprofloxacin", "Fluvoxamine", "Mexiletine", "Propafenone",
			"Zileuton", "Amiodarone", "Ticlopidine" };

	// Tranylcypromine-procarbazine
	private static String[] tranylcypromineObjectItem = new String[] { "Tranylcypromine" };
	private static String[] tranylcyprominePercipitantItem = new String[] { "Procarbazine" };

	// Triptans-MAO inhibitors
	private static String[] triptansObjectItem = new String[] { "Sumatriptan",
			"Zolmitriptan", "Rizatriptan" };
	private static String[] triptansPercipitantItem = new String[] {
			"Tranylcypromine", "Phenelzine", "Isocarboxazid", "Moclobamide",
			"Methylene blue" };

	private static String[][] objectClasses = new String[][] {
			amphetamineObjectItems, atazanavirObjectItem, febuxostatObjectItem,
			fluoxetineObjectItem, irinotecanItems, narcoticAnalgesics,
			tricyclicObjectItem, qtObjectItem, ramelteonObjectItem,
			rifampinObjectItem, hmgObjectItem, telithromycinObjectItem,
			tizanidineObjectItem, tranylcypromineObjectItem, triptansObjectItem };

	private static String[][] percipitantClasses = new String[][] {
			amphetaminePercipitantItem, protonPumpPercipitantItem,
			azathioprinePercipitantItem, amphetaminePercipitantItem,
			ketoconazoleItems, amphetaminePercipitantItem,
			amphetaminePercipitantItem, qtPercipitantItem,
			fluvoxaminePercipitantItem, ritonavirPercipitantItem,
			hmgPercipitantItem, telithromycinPercipitantItem,
			tizanidinePercipitantItem, tranylcyprominePercipitantItem,
			triptansPercipitantItem };

	public DBInit(Context context) {
		dao = new DDIPairDao(context);
		if (dao.getAll().isEmpty()) {
			ObjectItemDao objectItemDao = new ObjectItemDao(context);
			PercipitantItemDao percipitantItemDao = new PercipitantItemDao(
					context);
			Date date = new Date();
	
			List<String> objectItemsDb = new ArrayList<String>();
			for (int i = 0, l = 0; i < objectClasses.length; i++) {
				for (int j = 0; j < objectClasses[i].length; j++) {
					if (objectItemDao.getObjectItemByName(objectClasses[i][j]) == null) {
						ObjectItem objectItem = new ObjectItem();
						objectItem.setId(String.valueOf(l++));
						objectItem.setName(objectClasses[i][j]);
						objectItemDao.insert(objectItem);
						objectItemsDb.add(objectClasses[i][j]);
					}
				}
			}
			List<String> percipitantItemsDb = new ArrayList<String>();
			for (int i = 0, l = 0; i < percipitantClasses.length; i++) {
				for (int j = 0; j < percipitantClasses[i].length; j++) {
					if (percipitantItemDao
							.getPercipitantItemByName(percipitantClasses[i][j]) == null) {
						date = new Date();
						PercipitantItem percipitantItem = new PercipitantItem();
						percipitantItem.setId(String.valueOf(l++));
						percipitantItem.setName(percipitantClasses[i][j]);
						percipitantItemDao.insert(percipitantItem);
						percipitantItemsDb.add(percipitantClasses[i][j]);
					}
				}
			}

			for (int i = 0; i < ddis.length; i++) {
				DDIPair ddiPair = new DDIPair();
				ddiPair.setId(ddiIds[i]);
				ddiPair.setMyDdi(false);
				ddiPair.setName(ddis[i]);
				ddiPair.setStatus(Status.KLINISCH_SIGNIFIKANTE_INTERAKTION);

				if (objectClasses[i] != null) {
					date = new Date();
					ObjectClass objectClass = new ObjectClass();
					objectClass.setId(String.valueOf(i));
					objectClass.setName(objectClassNames[i]);
					ddiPair.setObjectClass(objectClass);
					List<ObjectItem> objectItems = new ArrayList<ObjectItem>();
					for (int l = 0; l < objectClasses[i].length; l++) {
						ObjectItem objectItem = objectItemDao
								.getObjectItemByName(objectClasses[i][l]);
						objectItems.add(objectItem);
					}

					objectClass.setObjectItems(objectItems);
				}
				if (percipitantClasses[i] != null) {
					date = new Date();
					PercipitantClass percipitantClass = new PercipitantClass();
					percipitantClass.setId(String.valueOf(i));
					percipitantClass.setName(percipitantClassNames[i]);
					ddiPair.setPercipitantClass(percipitantClass);
					List<PercipitantItem> percpitantItems = new ArrayList<PercipitantItem>();

					for (int l = 0; l < percipitantClasses[i].length; l++) {
						PercipitantItem percipitantItem = percipitantItemDao
								.getPercipitantItemByName(percipitantClasses[i][l]);
						percpitantItems.add(percipitantItem);
					}

					percipitantClass.setPercipitantItems(percpitantItems);
				}
				System.out.println(ddiPair.getName());
				dao.insert(ddiPair);
			}
		}
	}
}
