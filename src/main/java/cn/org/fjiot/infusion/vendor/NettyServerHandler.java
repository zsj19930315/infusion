//package cn.org.fjiot.infusion.vendor;
//
//import javax.swing.Spring;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import cn.org.fjiot.infusion.entity.Device;
//import cn.org.fjiot.infusion.entity.Message;
//import cn.org.fjiot.infusion.entity.Receive;
//import cn.org.fjiot.infusion.entity.Record;
//import cn.org.fjiot.infusion.entity.Task;
//import cn.org.fjiot.infusion.enums.Type;
//import cn.org.fjiot.infusion.service.DeviceService;
//import cn.org.fjiot.infusion.service.DeviceServiceImpl;
//import cn.org.fjiot.infusion.service.MessageService;
//import cn.org.fjiot.infusion.service.MessageServiceImpl;
//import cn.org.fjiot.infusion.service.ReceiveService;
//import cn.org.fjiot.infusion.service.RecordService;
//import cn.org.fjiot.infusion.service.RecordServiceImpl;
//import cn.org.fjiot.infusion.service.TaskService;
//import cn.org.fjiot.infusion.service.TaskServiceImpl;
//import cn.org.fjiot.infusion.util.MathUtil;
//import cn.org.fjiot.infusion.util.SpringUtil;
//import cn.org.fjiot.infusion.util.TimeUtil;
//import cn.org.fjiot.infusion.vendor.netty.DeviceServerHandler;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.DatagramChannel;
//import io.netty.channel.socket.DatagramPacket;
//import io.netty.util.CharsetUtil;
//
//public class NettyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);
//	
//	private static final String ELEC = "02";
//	
//	private static final String START = "03";
//	
//	private static final String END = "07";
//	
//	private static final String SUSPEND = "09";
//	
//	private static final String HEART = "01";
//	
//	private static final String REG = "0C";
//	
//	private static final String STARTED = "04";
//	
//	private static final String ALARM = "05";
//	
//	private static final String REPLY = "06";
//	
//	private static final Long THRESHOLE = 10L;
//	
//	private static final String LENGTH1 = "01";
//	
//	private static final String LENGTH2 = "02";
//	
//	private static DeviceService deviceService = (DeviceService) SpringUtil.getBean(DeviceServiceImpl.class);
//	
//	private static TaskService taskService = (TaskService) SpringUtil.getBean(TaskServiceImpl.class);
//	
//	private static RecordService recordService = (RecordService) SpringUtil.getBean(RecordServiceImpl.class);
//	
//	private static MessageService messageService = (cn.org.fjiot.infusion.service.MessageService) SpringUtil.getBean(MessageServiceImpl.class);
//
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
//		String req = msg.content().toString(CharsetUtil.UTF_8).trim().replace(" ", "");
//		if (19 > req.length()) {
//			LOGGER.warn("无效数据:{}", req);
//			return;
//		}
//		if (req.substring(16, req.length() - 2).length() / 2 != Integer.parseInt(req.substring(14, 16))) {
//			LOGGER.warn("还是无效数据:{}", req);
//			return;
//		}
//		Message receive = saveMessage(req, Type.RECEIVE);
//		Device device = hasReg(receive);
//		if (null == device && !REG.equalsIgnoreCase(receive.getFunCode())) {
//			LOGGER.warn("该设备{}未注册，不能使用", receive.getDeviceNo());
//			return;
//		}
//		String resp = "";
//		switch (receive.getFunCode()) {
//		case REG:
//			resp = regHandler(receive, device);
//			break;
//		case ELEC:
//			resp = elecHandler(receive, device);
//			break;
//		case START:
//			resp = startHandler(receive, device);
//			break;
//		case END:
//			resp = endHandler(receive, device);
//			break;
//		case SUSPEND:
//			resp = suspendHandler(receive, device);
//			break;
//		case HEART:
//			resp = heartHandler(receive, device);
//			break;
//		default:
//			break;
//		}
//		ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(resp, CharsetUtil.UTF_8), msg.sender()));
//	}
//	
//	private Message saveMessage(String req, Type type) {
//		Message message = new Message();
//		message.setHead(req.substring(0, 2));
//		message.setVersion(req.substring(2, 6));
//		message.setDeviceNo(req.substring(6, 12));
//		message.setFunCode(req.substring(12, 14));
//		message.setLength(req.substring(14, 16));
//		message.setContent(req.substring(16, req.length() - 2));
//		message.setCheckCode(req.substring(req.length() - 2, req.length()));
//		message.setCreateTime(TimeUtil.now());
//		message.setType(type);
//		messageService.save(message);
//		return message;
//	}
//	
//	private Device hasReg(Receive receive) {
//		Device device = deviceService.findBy(receive.getDeviceNo());
//		return device;
//	}
//	
//	private String regHandler(Receive receive, Device device) {
//		if (null != device) {
//			LOGGER.error("该设备{}已注册", receive.getDeviceNo());
//			return rec2Replay(receive);
//		}
//		device = new Device();
//		device.setDeviceNo(receive.getDeviceNo());
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setElectric(MathUtil.xstring2String(receive.getContent()));
//		device.setIsLowPower((Integer.parseInt(device.getElectric()) < 15) ? true : false);
//		device.setIsBusy(false);
//		deviceService.add(device);
//		return rec2Replay(receive);
//	}
//	
//	private String elecHandler(Receive receive, Device device) {
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setElectric(MathUtil.xstring2String(receive.getContent()));
//		device.setIsLowPower((Integer.parseInt(device.getElectric()) < 15) ? true : false);
//		deviceService.update(device);
//		return rec2Replay(receive);
//	}
//	
//	private String startHandler(Receive receive, Device device) {
//		if (device.getIsBusy()) {
//			LOGGER.warn("上个任务未完成，重新开始新任务");
//			device.setIsBusy(false);
//			Task task = device.getTask();
//			task.setDevice(null);
//			taskService.update(task);
//			device.setTask(null);
//			deviceService.update(device);
//			Record record = new Record();
//			record.setDevice(device);
//			record.setRecordNo(task.getId());
//			record.setResult("未正常关闭该任务，具体详情不告诉你=.=");
//			recordService.add(record);
//			return rec2Replay(receive);
//		}
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setIsBusy(true);
//		Task task = new Task();
//		task.setDevice(device);
//		task.setIsStart(true);
//		task.setIsError(false);
//		task.setIsLowLevel(false);
//		task.setIsPercent(false);
//		task.setIsSuspend(false);
//		task.setStartTime(TimeUtil.now());
//		task.setWeightSum(MathUtil.xstring2Long(receive.getContent()));
//		task.setWeightThreshold(task.getWeightSum() - THRESHOLE);
//		task.setWeightCur(0L);
//		device.setTask(task);
//		deviceService.update(device);
//		return rec2Started(receive);
//	}
//	
//	private String endHandler(Receive receive, Device device) {
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setIsBusy(false);
//		Task task = device.getTask();
//		task.setEndTime(TimeUtil.now());
//		task.setIsError(false);
//		task.setIsLowLevel(false);
//		task.setIsPercent(false);
//		task.setIsStart(false);
//		task.setIsSuspend(false);
//		task.setDevice(null);
//		taskService.update(task);
//		device.setTask(null);
//		deviceService.update(device);
//		Record record = new Record();
//		record.setDevice(device);
//		record.setRecordNo(task.getId());
//		record.setResult("任务正常结束");
//		recordService.add(record);
//		return rec2Replay(receive);
//	}
//	
//	private String suspendHandler(Receive receive, Device device) {
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setIsBusy(true);
//		Task task = device.getTask();
//		task.setIsError(false);
//		task.setIsSuspend(true);
//		task.setRestTime(TimeUtil.now());
//		taskService.update(task);
//		deviceService.update(device);
//		return rec2Replay(receive);
//	}
//	
//	private String heartHandler(Receive receive, Device device) {
//		device.setIsLose(false);
//		device.setActiveTime(TimeUtil.now());
//		device.setIsBusy(true);
//		Task task = device.getTask();
//		Long lastWeight = task.getWeightCur();
//		task.setWeightCur(MathUtil.xstring2Long(receive.getContent()));
//		task.setIsError((lastWeight > task.getWeightCur()) ? true : false);
//		task.setIsLowLevel((task.getWeightCur() > task.getWeightThreshold()) ? true : false);
//		task.setIsPercent((task.getWeightCur() > (task.getWeightSum() / 10 * 9)) ? true : false);
//		task.setIsSuspend(false);
//		task.setUpdateTime(TimeUtil.now());
//		deviceService.update(device);
//		if (task.getIsLowLevel()) {
//			return rec2Alarm(receive);
//		}
//		return rec2Replay(receive);
//	}
//	
//	private String rec2Replay(Receive receive) {
//		receive.setFunCode(REPLY);
//		receive.setLength(LENGTH2);
//		receive.setContent(TimeUtil.next());
//		return receive2String(receive);
//	}
//	
//	private String rec2Started(Receive receive) {
//		receive.setFunCode(STARTED);
//		receive.setLength(LENGTH2);
//		return receive2String(receive);
//	}
//	
//	private String rec2Alarm(Receive receive) {
//		receive.setFunCode(ALARM);
//		receive.setLength(LENGTH1);
//		receive.setContent("00");
//		return receive2String(receive);
//	}
//	
//	private String receive2String(Receive receive) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(receive.getHead());
//		sb.append(receive.getVersion());
//		sb.append(receive.getDeviceNo());
//		sb.append(receive.getFunCode());
//		sb.append(receive.getLength());
//		sb.append(receive.getContent());
//		sb.append(receive.getCheCode());
//		return sb.toString();
//	}
//
//}
