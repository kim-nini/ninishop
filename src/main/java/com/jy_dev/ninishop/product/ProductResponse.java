package com.jy_dev.ninishop.product;

import com.jy_dev.ninishop.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductResponse {

    @Getter
    @Setter
    public static class findAllDTO{
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

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
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

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
            private int id;
            private String optionName;
            private int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }



}
