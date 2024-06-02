package org.example.productservice.product;

import lombok.Getter;
import lombok.Setter;
import org.example.productservice.option.Option;

import java.util.List;

public class ProductResponse {

    @Getter
    @Setter
    public static class findAllDTO{
        private long id;
        private String productName;
        private String description;
        private String image;
        private long price;

        public findAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();

        }
    }

    @Getter
    @Setter
    public static class ProductDetailDTO{
        private long id;
        private String productName;
        private String description;
        private String image;
        private long price;

        private List<OptionDTO> options;


        public ProductDetailDTO(Product product, List<Option> options) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.options = options.stream().map(OptionDTO::new).toList();

        }

        @Getter
        @Setter
        public class OptionDTO{
            private long id;
            private String optionName;
            private long price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }



}
