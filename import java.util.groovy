import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Lớp Task dùng để lưu trữ thông tin của một công việc.
 */
class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    // Getters
    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }
    
    // Phương thức đánh dấu công việc hoàn thành
    public void markDone() { this.isDone = true; }

    /**
     * Ghi đè phương thức toString để hiển thị trạng thái và mô tả công việc.
     */
    @Override
    public String toString() {
        String status = isDone ? "[HOÀN THÀNH]" : "[CHƯA XONG]";
        return status + " " + description;
    }
}

/**
 * Lớp chính của ứng dụng ToDo chạy trên Console.
 */
public class TodoApp {
    private List<Task> tasks = new ArrayList<>();
    // Sử dụng Scanner để đọc dữ liệu từ console
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.run();
    }

    /**
     * Vòng lặp chính của ứng dụng.
     */
    public void run() {
        boolean running = true;
        System.out.println("--- ỨNG DỤNG GHI CHÚ ĐƠN GIẢN (JAVA CONSOLE) ---");

        while (running) {
            displayMenu();
            try {
                // Đọc lựa chọn của người dùng
                int choice = scanner.nextInt();
                scanner.nextLine(); // Tiêu thụ ký tự xuống dòng (newline)

                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        viewTasks();
                        break;
                    case 3:
                        markTaskCompleted();
                        break;
                    case 4:
                        deleteTask();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Tạm biệt! Ứng dụng kết thúc. (Dữ liệu không được lưu.)");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } catch (InputMismatchException e) {
                // Xử lý lỗi nếu người dùng nhập ký tự thay vì số
                System.out.println("Lỗi: Vui lòng nhập một số.");
                scanner.nextLine(); // Tiêu thụ đầu vào không hợp lệ để tránh vòng lặp vô hạn
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi không xác định: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Hiển thị menu chức năng cho người dùng.
     */
    private void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Thêm công việc mới");
        System.out.println("2. Xem danh sách công việc");
        System.out.println("3. Đánh dấu công việc hoàn thành");
        System.out.println("4. Xóa công việc");
        System.out.println("5. Thoát");
        System.out.print("Nhập lựa chọn của bạn: ");
    }

    /**
     * Thêm một công việc mới vào danh sách.
     */
    private void addTask() {
        System.out.print("Nhập nội dung công việc: ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            tasks.add(new Task(description));
            System.out.println("Đã thêm công việc thành công.");
        } else {
            System.out.println("Nội dung công việc không được để trống.");
        }
    }

    /**
     * Hiển thị toàn bộ danh sách công việc.
     */
    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Danh sách công việc trống.");
            return;
        }
        System.out.println("\n--- DANH SÁCH CÔNG VIỆC ---");
        for (int i = 0; i < tasks.size(); i++) {
            // Hiển thị số thứ tự (i + 1) để người dùng dễ tương tác
            System.out.printf("%d. %s\n", (i + 1), tasks.get(i).toString());
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Đánh dấu một công việc cụ thể đã hoàn thành.
     */
    private void markTaskCompleted() {
        viewTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Nhập số thứ tự công việc muốn đánh dấu HOÀN THÀNH: ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Tiêu thụ newline

            // Kiểm tra chỉ số có hợp lệ không
            if (index > 0 && index <= tasks.size()) {
                Task taskToMark = tasks.get(index - 1);
                if (!taskToMark.isDone()) {
                    taskToMark.markDone();
                    System.out.printf("Đã đánh dấu công việc '%s' là HOÀN THÀNH.\n", taskToMark.getDescription());
                } else {
                     System.out.println("Công việc này đã được hoàn thành rồi.");
                }
            } else {
                System.out.println("Số thứ tự không hợp lệ.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập một số hợp lệ.");
            scanner.nextLine(); // Tiêu thụ đầu vào không hợp lệ
        }
    }

    /**
     * Xóa một công việc khỏi danh sách.
     */
    private void deleteTask() {
        viewTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Nhập số thứ tự công việc muốn XÓA: ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Tiêu thụ newline

            // Kiểm tra chỉ số có hợp lệ không
            if (index > 0 && index <= tasks.size()) {
                Task removedTask = tasks.remove(index - 1);
                System.out.printf("Đã xóa công việc: '%s'\n", removedTask.getDescription());
            } else {
                System.out.println("Số thứ tự không hợp lệ.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập một số hợp lệ.");
            scanner.nextLine(); // Tiêu thụ đầu vào không hợp lệ
        }
    }
}