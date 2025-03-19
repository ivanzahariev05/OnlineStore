package softuni.bg.supplementsonlinestore.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NotificationPreference {

    private UUID id;

    private boolean enabled;
}
