package net.sf.l2j.gameserver.model.actor.poly;

import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.UserInfo;

public class ObjectPoly
{
	private final L2Object _activeObject;
	private int _polyId;
	private String _polyType;
	private NpcTemplate _npcTemplate;
	
	public ObjectPoly(L2Object activeObject)
	{
		_activeObject = activeObject;
	}
	
	public boolean setPolyInfo(String polyType, String polyId)
	{
		int id = Integer.parseInt(polyId);
		if ("npc".equals(polyType))
		{
			NpcTemplate template = NpcTable.getInstance().getTemplate(id);
			if (template == null)
				return false;
			
			_npcTemplate = template;
		}
		
		setPolyId(id);
		setPolyType(polyType);
		
		_activeObject.decayMe();
		_activeObject.spawnMe(_activeObject.getX(), _activeObject.getY(), _activeObject.getZ());
		
		if (_activeObject instanceof L2PcInstance)
			((L2PcInstance) _activeObject).sendPacket(new UserInfo(((L2PcInstance) _activeObject)));
		
		return true;
	}
	
	public final L2Object getActiveObject()
	{
		return _activeObject;
	}
	
	public final boolean isMorphed()
	{
		return getPolyType() != null;
	}
	
	public final int getPolyId()
	{
		return _polyId;
	}
	
	public final void setPolyId(int value)
	{
		_polyId = value;
	}
	
	public final String getPolyType()
	{
		return _polyType;
	}
	
	public final void setPolyType(String value)
	{
		_polyType = value;
	}
	
	public final NpcTemplate getNpcTemplate()
	{
		return _npcTemplate;
	}
}