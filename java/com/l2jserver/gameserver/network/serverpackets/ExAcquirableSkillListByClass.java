/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.gameserver.network.serverpackets;

import java.util.List;

import com.l2jserver.gameserver.model.L2SkillLearn;
import com.l2jserver.gameserver.model.base.AcquireSkillType;
import com.l2jserver.gameserver.model.holders.ItemHolder;

/**
 * @author UnAfraid
 */
public class ExAcquirableSkillListByClass extends L2GameServerPacket
{
	final List<L2SkillLearn> _learnable;
	final AcquireSkillType _type;
	
	public ExAcquirableSkillListByClass(List<L2SkillLearn> learnable, AcquireSkillType type)
	{
		_learnable = learnable;
		_type = type;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xFA);
		writeH(_type.getId());
		writeH(_learnable.size());
		for (L2SkillLearn skill : _learnable)
		{
			writeD(skill.getSkillId());
			writeD(skill.getSkillLevel());
			writeQ(skill.getLevelUpSp());
			writeC(skill.getGetLevel());
			writeC(0x00 /* skill.getGetDualClassLevel() */); // TODO: Dual class lvl !!!
			
			List<ItemHolder> reqItems = skill.getRequiredItems();
			
			writeC(reqItems.size());
			for (ItemHolder ih : reqItems)
			{
				writeD(ih.getId());
				writeQ(ih.getCount());
			}
			
			writeC(0x00); // TODO: Required Skill count
			/**
			 * for (SkillHolder sh : reqSkills) { writeD(sh.getId()); writeD(sh.getLevel()); }
			 */
		}
	}
}
