package com.johanno.rideablespiders;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;
//import static org.objectweb.asm.Opcodes.IFNONNULL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class CoreTransform implements IClassTransformer
{
	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) 
	{

		
			
			
		//Check if the JVM is about to process the te.class or the EntityCreeper.class
//			if (arg0.equals("bhp") || arg0.equals("net.minecraft.client.renderer.entity.RenderSpider"))
//			{
//				arg2 = patchClassInJar(arg0, arg2);
//			}
//			if (arg0.equals("tt") || arg0.equals("net.minecraft.entity.monster.EntitySpider"))
//			{
//				arg2 = patchClassInJar(arg0, arg2);
//			}
			
//			if (arg0.equals("axv")) 
//			{
//				System.out.println("********* INSIDE OBFUSCATED guiinv TRANSFORMER ABOUT TO PATCH: " + arg0);
//				arg2 = patchClassASM(arg0, false, arg2, "A_", "()V", "a", "(Laut;)V", true);
//			}
//
//			if (arg0.equals("net.minecraft.client.gui.inventory.GuiInventory"))
//			{
//				System.out.println("********* INSIDE guiinv TRANSFORMER ABOUT TO PATCH: " + arg0);
//				arg2 = patchClassASM(arg0, false, arg2, "initGui", "()V", "actionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V", false);
//			}
//			
//			if (arg0.equals("axm"))
//			{
//				System.out.println("********* INSIDE guiinv TRANSFORMER ABOUT TO PATCH: " + arg0);
//				arg2 = patchClassASM(arg0, true, arg2, "A_", "()V", "a", "(Laut;)V", true);
//			}
//			
//			if (arg0.equals("net.minecraft.client.gui.inventory.GuiContainerCreative"))
//			{
//				System.out.println("********* INSIDE guiinv TRANSFORMER ABOUT TO PATCH: " + arg0);
//				arg2 = patchClassASM(arg0, true, arg2, "initGui", "()V", "actionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V", false);
//			}
			
			
			
			if (arg0.equals("mx"))
			{
				System.out.println("********* INSIDE netserverh TRANSFORMER ABOUT TO PATCH: " + arg0);
				arg2 = patchClassASM(arg0, arg2, "a", "(Liy;)V", true);
			}
			
			if (arg0.equals("net.minecraft.network.NetHandlerPlayServer"))
			{
				System.out.println("********* INSIDE netserverh TRANSFORMER ABOUT TO PATCH: " + arg0);
				arg2 = patchClassASM(arg0, arg2, "processInput", "(Lnet/minecraft/network/play/client/C0CPacketInput;)V", false);
			}			
			
			return arg2;
	}
		
		public byte[] patchClassASM(String arg0, byte[] bytes, String methodename1, String methodparsret1, boolean obfuescated) 
		{
			return patchClassASM(arg0, false, bytes, methodename1, methodparsret1, null, null, obfuescated);
		}
	
		public byte[] patchClassASM(String arg0, boolean creative, byte[] bytes, String methodename1, String methodparsret1, String methodename2, String methodparsret2, boolean obfuescated) 
		{
			
	//		name = name.replace('.', '/');
	
			//set up ASM class manipulation stuff. Consult the ASM docs for details
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(bytes);
			classReader.accept(classNode, 0);
	
			//Now we loop over all of the methods declared inside the Explosion class until we get to the targetMethodName "doExplosionB"
	
			Iterator<MethodNode> methods = classNode.methods.iterator();
						
			while(methods.hasNext())
			{
				MethodNode m = methods.next();
				
//				if(methodename2 != null)
//				{
//					//Check if this is doExplosionB and it's method signature is (Z)V which means that it accepts a boolean (Z) and returns a void (V)
//					if ((m.name.equals(methodename1) && m.desc.equals(methodparsret1)))
//					{
//						System.out.println("********* Inside target method!1");
//						this.guinv(arg0, m, obfuescated, creative);
//					}
//					else if ((m.name.equals(methodename2) && m.desc.equals(methodparsret2)))
//					{
//						System.out.println("********* Inside target method!2");
//						this.gui2(m, obfuescated, creative, arg0);
//					}			
//				}
//				else
//				{
					if ((m.name.equals(methodename1) && m.desc.equals(methodparsret1)))
					{
						System.out.println("********* Inside target method!3");
						this.netservh(m, obfuescated, arg0);
						break;
					}
//				}
			}
			
			
				//ASM specific for cleaning up and returning the final bytes for JVM processing.
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				classNode.accept(writer);
				return writer.toByteArray();
		}
	
		private AbstractInsnNode gettarget(MethodNode m, int opcode)
		{
			return this.gettarget(m, opcode, 0);
		}
		
		private AbstractInsnNode gettarget(MethodNode m, int opcode, int times)
		{

			AbstractInsnNode currentNode = null;
			AbstractInsnNode targetNode = null;
			
			Iterator<AbstractInsnNode> iter = m.instructions.iterator();

			//Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
			
			 while (iter.hasNext())
	         {
	         currentNode = iter.next();
	        System.out.println(currentNode.getOpcode());
	         //Found it! save the index location of instruction FDIV and the node for this instruction
	         if (currentNode.getOpcode() == opcode)
	         {
	        	 times--;
	        	 if(times <= 0)
	        	 {
	                 targetNode = currentNode;
	                 break;
	        	 }
	         }
	         }
			 if(targetNode == null){System.out.println("****************Error by patching");}
			 return targetNode;
		}
	
		/*
		private void guinv(String arg0, MethodNode m, boolean obf, boolean iscrea)
		{
			AbstractInsnNode targetNode = this.gettarget(m, RETURN);

			// make new instruction list
			InsnList toInject = new InsnList();

			//add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE*GuiContainerCreative
			LabelNode l1 = new LabelNode();
			toInject.add(l1);
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg0 :"net/minecraft/client/gui/inventory/" + (iscrea ? "GuiContainerCreative":"GuiInventory"), obf?"f":"mc", obf?"Latv;":"Lnet/minecraft/client/Minecraft;"));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"atv":"net/minecraft/client/Minecraft", obf?"h":"thePlayer", obf?"Lbdi;":"Lnet/minecraft/client/entity/EntityClientPlayerMP;"));
			toInject.add(new VarInsnNode(ASTORE, 1));
			LabelNode l2 = new LabelNode();
			toInject.add(l2);
			toInject.add(new VarInsnNode(ALOAD, 1));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"nn":"net/minecraft/entity/player/EntityPlayer", obf?"o":"ridingEntity", obf?"Lnn;":"Lnet/minecraft/entity/Entity;"));
			LabelNode l3 = new LabelNode();
			toInject.add(new JumpInsnNode(IFNULL, l3));
			toInject.add(new VarInsnNode(ALOAD, 1));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"nn":"net/minecraft/entity/player/EntityPlayer", obf?"o":"ridingEntity", obf?"Lnn;":"Lnet/minecraft/entity/Entity;"));
			toInject.add(new TypeInsnNode(INSTANCEOF, "net/minecraft/entity/monster/EntitySpider"));
			toInject.add(new JumpInsnNode(IFEQ, l3));
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg0 :"net/minecraft/client/gui/inventory/" + (iscrea ? "GuiContainerCreative":"GuiInventory"), obf?"f":"mc", obf?"Latv;":"Lnet/minecraft/client/Minecraft;"));
			toInject.add(new MethodInsnNode(INVOKEVIRTUAL, obf?"atv":"net/minecraft/client/Minecraft", obf?"B":"isSingleplayer", "()Z"));
			toInject.add(new JumpInsnNode(IFEQ, l3));

			LabelNode l4 = new LabelNode();
			toInject.add(l4);
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg0:"net/minecraft/client/gui/inventory/"+(iscrea?"GuiContainerCreative":"GuiInventory"), obf?"i":"buttonList", "Ljava/util/List;"));
			toInject.add(new TypeInsnNode(NEW, "net/minecraft/client/gui/GuiButton"));
			toInject.add(new InsnNode(DUP));
			toInject.add(new InsnNode(ICONST_2));
			toInject.add(new InsnNode(ICONST_0));
			toInject.add(new InsnNode(ICONST_0));
			toInject.add(new VarInsnNode(BIPUSH, 100));
			toInject.add(new VarInsnNode(BIPUSH, 20));
			toInject.add(new LdcInsnNode("Spider inventory"));
			toInject.add(new MethodInsnNode(INVOKESPECIAL, "net/minecraft/client/gui/GuiButton", "<init>", "(IIIIILjava/lang/String;)V"));
			toInject.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z"));
			toInject.add(new InsnNode(POP));
			toInject.add(l3);
			toInject.add(new FrameNode(Opcodes.F_APPEND,2, new Object[] {"net/minecraft/world/World", "net/minecraft/entity/player/EntityPlayer"}, 0, null));
			
			// add the added code to the nstruction list
			// You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore)
			m.instructions.insertBefore(targetNode, toInject);
			
			
			System.out.println("Patching Complete!");
		}
		
		private void gui2(MethodNode m, boolean obf, boolean iscrea, String arg)
		{

			AbstractInsnNode targetNode = this.gettarget(m, ALOAD);

			// make new instruction list
			InsnList toInject = new InsnList();

			//add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE*
			
			LabelNode l1 = new LabelNode();
			toInject.add(l1);
			toInject.add(new VarInsnNode(ALOAD, 1));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"aut":"net/minecraft/client/gui/GuiButton", obf?"g":"id", "I"));
			toInject.add(new InsnNode(ICONST_2));
			LabelNode l2 = new LabelNode();
			toInject.add(new JumpInsnNode(IF_ICMPNE, l2));
			
			LabelNode l3 = new LabelNode();
			toInject.add(l3);
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg :"net/minecraft/client/gui/inventory/" + (iscrea ? "GuiContainerCreative":"GuiInventory"), obf?"f":"mc", obf?"Latv;":"Lnet/minecraft/client/Minecraft;"));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"atv":"net/minecraft/client/Minecraft", obf?"h":"thePlayer", obf?"Lbdi;":"Lnet/minecraft/client/entity/EntityClientPlayerMP;"));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"nn":"net/minecraft/entity/player/EntityPlayer", obf?"o":"ridingEntity", obf?"Lnn;":"Lnet/minecraft/entity/Entity;"));
		    toInject.add(new TypeInsnNode(CHECKCAST, "net/minecraft/entity/monster/EntitySpider"));
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg :"net/minecraft/client/gui/inventory/" + (iscrea ? "GuiContainerCreative":"GuiInventory"), obf?"f":"mc", obf?"Latv;":"Lnet/minecraft/client/Minecraft;"));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"atv":"net/minecraft/client/Minecraft", obf?"h":"thePlayer", obf?"Lbdi;":"Lnet/minecraft/client/entity/EntityClientPlayerMP;"));
			toInject.add(new MethodInsnNode(INVOKEVIRTUAL, obf?"tt":"net/minecraft/entity/monster/EntitySpider", "opengui", obf?"(Luf;)V":"(Lnet/minecraft/entity/player/EntityPlayer;)V"));	
			toInject.add(l2);
			toInject.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			LabelNode l4 = new LabelNode();
			toInject.add(l4);
			
			// add the added code to the nstruction list
			// You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore)
			m.instructions.insertBefore(targetNode, toInject);
			
			
			System.out.println("Patching Complete!");
		}*/
		
		
		private void netservh(MethodNode m, boolean obf, String arg)
		{
			AbstractInsnNode targetNode = this.gettarget(m, RETURN);

			// make new instruction list
			InsnList toInject = new InsnList();
			
			LabelNode l1 = new LabelNode();
			toInject.add(l1);
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, obf?arg:"net/minecraft/network/NetHandlerPlayServer", obf?"b":"playerEntity", obf?"Lmm;":"Lnet/minecraft/entity/player/EntityPlayerMP;"));
			toInject.add(new FieldInsnNode(GETFIELD, obf?"qn":"net/minecraft/entity/Entity", obf?"n":"ridingEntity", obf?"Lqn;":"Lnet/minecraft/entity/Entity;"));
			toInject.add(new VarInsnNode(ASTORE, 2));
			LabelNode l4 = new LabelNode();
			toInject.add(l4);
			toInject.add(new VarInsnNode(ALOAD, 2));
			LabelNode l2 = new LabelNode();
			toInject.add(new JumpInsnNode(IFNULL, l2));
			toInject.add(new VarInsnNode(ALOAD, 2));
			toInject.add(new TypeInsnNode(INSTANCEOF, "com/johanno/rideablespiders/EntityRSpider"));
			toInject.add(new JumpInsnNode(IFEQ, l2));
			toInject.add(new VarInsnNode(ALOAD, 1));
			toInject.add(new MethodInsnNode(INVOKEVIRTUAL, obf?"iy":"net/minecraft/network/play/client/C0CPacketInput", obf?"e":"func_149618_e", "()Z"));
			toInject.add(new JumpInsnNode(IFEQ, l2));
			LabelNode l3 = new LabelNode();
		    toInject.add(l3);
		    toInject.add(new VarInsnNode(ALOAD, 2));
		    toInject.add(new TypeInsnNode(CHECKCAST, "com/johanno/rideablespiders/EntityRSpider"));
		    toInject.add(new InsnNode(ICONST_1));
		    toInject.add(new MethodInsnNode(INVOKEVIRTUAL, /*obf?"tt":*/"com/johanno/rideablespiders/EntityRSpider", "setJump", "(Z)V"));
		    toInject.add(l2);
		    toInject.add(new FrameNode(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/entity/Entity"}, 0, null));

		    if(targetNode!=null)
		    	m.instructions.insertBefore(targetNode, toInject);
		   
		    System.out.println("*********Patching complete!");
		}
		
//		mv.visitVarInsn(ALOAD, 0);								 
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/network/NetHandlerPlayServer", "playerEntity", "Lnet/minecraft/entity/player/EntityPlayerMP;");
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayerMP", "ridingEntity", "Lnet/minecraft/entity/Entity;");
//		mv.visitTypeInsn(INSTANCEOF, "com/johanno/rideablespiders/EntityRSpider");
//		Label l2 = new Label();
//		mv.visitJumpInsn(IFEQ, l2);
//		Label l3 = new Label();
//		mv.visitLabel(l3);
//		mv.visitLineNumber(205, l3);
//		mv.visitVarInsn(ALOAD, 1);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/network/play/client/C0CPacketInput", "func_149618_e", "()Z", false);
//		mv.visitJumpInsn(IFEQ, l2);
//		Label l4 = new Label();
//		mv.visitLabel(l4);
//		mv.visitLineNumber(206, l4);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/network/NetHandlerPlayServer", "playerEntity", "Lnet/minecraft/entity/player/EntityPlayerMP;");
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/player/EntityPlayerMP", "ridingEntity", "Lnet/minecraft/entity/Entity;");
//		mv.visitTypeInsn(CHECKCAST, "com/johanno/rideablespiders/EntityRSpider");
//		mv.visitInsn(ICONST_1);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "com/johanno/rideablespiders/EntityRSpider", "setJump", "(Z)V", false);
//		mv.visitLabel(l2);
//		mv.visitLineNumber(208, l2);
//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		mv.visitInsn(RETURN);
//		Label l5 = new Label();
//		mv.visitLabel(l5);
//		mv.visitLocalVariable("this", "Lnet/minecraft/network/NetHandlerPlayServer;", null, l0, l5, 0);
//		mv.visitLocalVariable("p_147358_1_", "Lnet/minecraft/network/play/client/C0CPacketInput;", null, l0, l5, 1);
//		mv.visitMaxs(5, 2);
		
		
		
	/*	
		
	//a small helper method that takes the class name we want to replace and our jar file.
	//It then uses the java zip library to open up the jar file and extract the classes.
	//Afterwards it serializes the class in bytes and pushes it on to the JVM.
	//with the original bytes that JVM was about to process ignored completly

	public byte[] patchClassInJar(String name, byte[] bytes) 
	{
		try 
		{
			System.out.println("********* INSIDE "+name+" TRANSFORMER ABOUT TO PATCH: ");
		//open the jar as zip
		ZipFile zip = new ZipFile(location);
		//find the file inside the zip that is called te.class or net.minecraft.entity.monster.EntityCreeper.class
		//replacing the . to / so it would look for net/minecraft/entity/monster/EntityCreeper.class
		ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
	
		if (entry == null) 
		{
			System.out.println(name + " not found in " + location.getName());
		} else
		{
		//serialize the class file into the bytes array
			InputStream zin = zip.getInputStream(entry);
			int size = (int) entry.getSize();
			bytes = new byte[size];
			int pos = 0;
			while (pos < size) {
			        int len = zin.read(bytes,pos,size-pos);
			        if (len == 0)
			                throw new IOException();
			        pos += len;
			}
			zin.close();
			
//			InputStream zin = zip.getInputStream(entry);
//	        bytes = new byte[(int)entry.getSize()];
//	        zin.read(bytes);
//	        zin.close();


		System.out.println("[RideAbleSpider]: Class " + name + " patched!");
		}
		zip.close();
		} catch (Exception e) {
		throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
		}
	
		//return the new bytes
		return bytes;
	}
	*/
}
