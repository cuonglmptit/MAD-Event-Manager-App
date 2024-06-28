package com.cuonglmptit.SpringServer.controller;

import com.cuonglmptit.SpringServer.model.ResponseData;
import com.cuonglmptit.SpringServer.model.event.Event;
import com.cuonglmptit.SpringServer.model.event.EventDAO;
import com.cuonglmptit.SpringServer.model.invite.Invite;
import com.cuonglmptit.SpringServer.model.invite.InviteDAO;
import com.cuonglmptit.SpringServer.model.invite.InviteDTO;
import com.cuonglmptit.SpringServer.model.invite.InviteStatus;
import com.cuonglmptit.SpringServer.model.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/invite")
public class InviteController {
    @Autowired
    InviteDAO inviteDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventDAO eventDAO;

    @PostMapping("/post")
    public ResponseEntity postInvite(@RequestBody InviteDTO inviteDTO) {
        try {
            Date currentDate = new Date();
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(currentDate.getTime());

            // Kiểm tra xem lời mời đã tồn tại hay không
            Invite existingInvite = inviteDAO.getInviteByUserIdAndEventId(inviteDTO.getToUser(), inviteDTO.getEventId());
            if (existingInvite != null) {
                existingInvite.setContext(inviteDTO.getContext());
                existingInvite.setCreatedDate(sqlDate);
                inviteDAO.save(existingInvite);
                System.out.println("Update invite time: " + existingInvite);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Cập nhật thời gian mời thành công"));
            } else {
                // Nếu lời mời chưa tồn tại, tạo mới lời mời và lưu vào cơ sở dữ liệu
                Invite invite = new Invite(false, userDAO.getUserByUserId(inviteDTO.getToUser()),
                        eventDAO.getEventByID(inviteDTO.getEventId()),
                        inviteDTO.getContext(),
                        inviteDTO.getInviteStatus(),
                        sqlDate
                );
                inviteDAO.save(invite);
                System.out.println("Post: " + invite);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Gửi lời mời thành công"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Lỗi gửi invite"));
        }
    }

    @GetMapping("/get-invited-user-of-event/{eventId}")
    public ArrayList<Invite> getInvitesByEventId(@PathVariable("eventId") int eventId) {
        return inviteDAO.getInvitesByEventId(eventId);
    }

    @GetMapping("/get-invite-accepted-user-of-event/{eventId}")
    public ResponseEntity getCountAcceptedInvitesByEventId(@PathVariable("eventId") int eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Get thành công", inviteDAO.getCountAcceptedInvitesByEventId(eventId)));
    }

    @GetMapping("/get-have-unread-invite/{userId}")
    public ResponseEntity getUnreadInviteByUserId(@PathVariable("userId") int userId) {
        try {
            ArrayList<Invite> invites = inviteDAO.findUnreadInvitesByUserId(userId);
            for (Invite invite : invites) {
                System.out.println(invite);
            }
            if (invites != null) {
                if (invites.size() > 0) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Get unread thành công", true));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Get unread thành công", false));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Get thất bại", false));
        }
    }

    @GetMapping("/get-invites-of-user/{userId}")
    public ArrayList<Invite> getInvitesByUserId(@PathVariable("userId") int userId) {
        return inviteDAO.findInvitesByUserId(userId);
    }

    @PutMapping("/send-quick-invite/{eventId}")
    public ResponseEntity putQuickInvite(@PathVariable("eventId") int eventId) {
        try {
            Date currentDate = new Date();
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(currentDate.getTime());
            ArrayList<Invite> invites = inviteDAO.findAcceptedInvitesByEventId(eventId);
            if (invites != null) {
                if (invites.size() > 0) {
                    for (Invite invite : invites) {
                        invite.setRead(false);
                        invite.setCreatedDate(sqlDate);
                        invite.setContext("Đã có thay đổi về event: " + invite.getEvent().getName()
                                + " EventID: " + invite.getEvent().getId()
                                + " sẽ bắt đầu vào ngày " + new SimpleDateFormat("EEEE, dd MM yyyy").format(invite.getEvent().getStartDate())
                                + " lúc " + invite.getEvent().getStartTime()
                                + " tại " + invite.getEvent().getAddress());
                        inviteDAO.save(invite);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Không có người dùng nào đã xác nhận tham gia sự kiện!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Không có người dùng nào đã xác nhận tham gia sự kiện!"));

            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Mời lại nhanh thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Thất bại!"));
        }
    }

    @PutMapping("/send-sorry/{eventId}")
    public ResponseEntity putSorryInvite(@PathVariable("eventId") int eventId, @RequestBody String message) {
        try {
            System.out.println("Put sorry: ");
            Date currentDate = new Date();
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(currentDate.getTime());
            ArrayList<Invite> invites = inviteDAO.getInvitesByEventId(eventId);
            if (invites != null) {
                if (invites.size() > 0) {
                    for (Invite invite : invites) {
                        invite.setRead(false);
                        invite.setCreatedDate(sqlDate);
                        invite.setContext(message + "\n////////\n"
                                + "Đã có thay đổi về event: " + invite.getEvent().getName()
                                + " EventID: " + invite.getEvent().getId()
                                + " sẽ dự kiến bị hủy");
                        inviteDAO.save(invite);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Không có người dùng nào đã xác nhận tham gia sự kiện!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Không có người dùng nào đã xác nhận tham gia sự kiện!"));

            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Thất bại!"));
        }
    }

    @PutMapping("/read/{inviteId}")
    public ResponseEntity markNotificationAsRead(@PathVariable("inviteId") int inviteId) {
        try {
            Invite invite = inviteDAO.findById(inviteId);
            if (invite != null) {
                invite.setRead(true);
                inviteDAO.save(invite); // Lưu thông báo đã cập nhật
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Cập nhật trạng thái đã đọc thành công"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(HttpStatus.NOT_FOUND.value(), "Không tìm thấy thông báo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Cập nhật trạng thái đã đọc thất bại"));
        }
    }

    @PutMapping("/read-all-of-user/{userId}")
    public ResponseEntity markAllNotificationAsRead(@PathVariable("userId") int userId, @RequestBody String message) {
        try {
            ArrayList<Invite> invites = inviteDAO.findInvitesByUserId(userId);
            for (Invite invite : invites) {
                invite.setRead(true);
                inviteDAO.save(invite); // Lưu thông báo đã cập nhật
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Cập nhật trạng thái đã đọc thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Cập nhật trạng thái đã đọc thất bại"));
        }
    }

    @PutMapping("/accept/{inviteId}")
    public ResponseEntity acceptInvite(@PathVariable("inviteId") int inviteId) {
        System.out.println("Put accept: "+inviteId);
        try {
            Invite invite = inviteDAO.findById(inviteId);
            invite.setInviteStatus(InviteStatus.ACCEPTED);
            inviteDAO.save(invite);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Cập nhật chấp nhận invite thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Cập nhật chấp nhận invite thất bại"));
        }
    }
    @PutMapping("/decline/{inviteId}")
    public ResponseEntity declineInvite(@PathVariable("inviteId") int inviteId) {
        System.out.println("Put decline: "+inviteId);
        try {
            Invite invite = inviteDAO.findById(inviteId);
            invite.setInviteStatus(InviteStatus.DECLINED);
            inviteDAO.save(invite);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Cập nhật decline invite thành công"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Cập nhật decline invite thất bại"));
        }
    }


}
